package Controller;

import Game.Account;
import Game.Battle.DuelArena;
import Game.Battle.Event;
import Game.MessageContext;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.restaction.MessageAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DuelBattleListener extends ListenerAdapter {

    private final static Logger logger = LogManager.getLogger(DuelLobby.class);
    private final DuelLobby lobby;
    private final PlayerStatusViewer viewer;

    public DuelBattleListener(DuelLobby lobby, PlayerStatusViewer viewer) {
        this.lobby = lobby;
        this.viewer = viewer;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot())
            return;

        DiscordHelper parser = new DiscordHelper();
        String input = parser.parseInput(event);

        if (input == null)
            return;

        MessageChannel room = event.getChannel();
        DuelArena battle = lobby.getSession(room);
        if (battle == null) {
            logger.info("No duel exists in channel {}. Ignoring Message.", room.getName());
            return;
        }
        Account sender = lobby.getAccount(event.getAuthor().getId());
        if (sender == null)
            return;

        MessageContext command = new MessageContext(sender.getPlayer(), input);
        List<Event> result = battle.processCommand(command);
        StringBuilder sb = new StringBuilder();
        {
            for (Event action: result)
            {
                action.applyTransformation(DiscordHelper.Markup.BOLD.value());
                String message = action.getEvent();
                sb.append(message).append("\n");
            }
        }
        logger.info("Channel: '{}' Dialogue: {}", event.getMessage().getChannel().getName(), sb.toString());

        MessageAction toSend;

        if (battle.lastMoveValid() && !battle.duelFinished()) {
            File f = null;
            try {
                f = viewer.createStatus(room, battle.getStatus());
                room.sendFile(f).queue();
                f.delete();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally {
                if (f != null)
                    f.delete();
            }
        }
        room.sendMessage(sb.toString()).queue();
        if (battle.duelFinished()) {
            lobby.clearWaitListAndSession(room);
        }
    }
}
