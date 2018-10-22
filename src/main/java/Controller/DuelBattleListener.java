package Controller;

import Game.Account;
import Game.Battle.DuelArena;
import Game.MessageContext;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DuelBattleListener extends ListenerAdapter {

    private final DuelLobby lobby;
    private final static Logger logger = LogManager.getLogger(DuelLobby.class);

    public DuelBattleListener(DuelLobby lobby)
    {
        this.lobby = lobby;
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        DiscordHelper parser = new DiscordHelper();
        String input = parser.parseInput(event);
        if (input == null)
            return;

        MessageChannel room = event.getChannel();
        DuelArena battle = lobby.getSession(room);
        if (battle == null)
            return;
        Account sender = lobby.getAccount(event.getAuthor().getId());
        if (sender == null)
            return;

        MessageContext command = new MessageContext(sender.getPlayer(), input);
        String result = battle.processCommand(command);
        room.sendMessage(result).queue();
        logger.info("Channel: '{}' Dialogue: {}", event.getMessage().getChannel(), result);
        if (battle.duelFinished())
        {
            lobby.clearSession(room);
        }
    }
}
