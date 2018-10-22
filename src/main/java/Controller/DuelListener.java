package Controller;

import Game.Account;
import Game.Battle.DuelArena;
import Game.Battle.Player;
import Game.Battle.PlayerHandleUpdater;
import Game.PlayerSpawner;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class DuelListener extends ListenerAdapter {


    private final DuelLobby lobby;
    private final PlayerSpawner spawner;
    private final static Logger logger = LogManager.getLogger(DuelListener.class);
    public DuelListener(DuelLobby lobby, PlayerSpawner spawner)
    {
        this.lobby = lobby;
        this.spawner = spawner;
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        logger.debug("Name: {} Message: {}", event.getAuthor().getName(), event.getMessage().getContentDisplay());

        DiscordHelper parser = new DiscordHelper();
        String input = parser.parseInput(event);
        if ((input == null) || !input.equalsIgnoreCase("dm")) {
            logger.debug("Ignored Message.");
            return;
        }

        MessageChannel channel = event.getMessage().getChannel();
        DuelArena battle = lobby.getSession(channel);
        if (battle != null) {
            logger.debug("Ignored Message.");
            return;
        }


        String snowFlake = event.getAuthor().getId();
        Account current = lobby.getAccount(snowFlake);
        if (current == null) {
            current = new Account(snowFlake, spawner.createFullyUnlocked());
            logger.info("Created new account for user: '{}'", event.getAuthor().getName());
            lobby.addAccount(current);
        }

        Account next = lobby.getWaiter(channel);
        if (next == null) {
            lobby.addWaiter(channel, lobby.getAccount(snowFlake));
            channel.sendMessage(String.format("%s is looking to fight!", event.getAuthor().getName())).queue();
            logger.info("Action: send player to waitlist. Channel: '{}' Name: '{}'",
                    event.getMessage().getChannel().getName(), event.getAuthor().getName());
            return;
       }
       if (next.getSnowFlakeId().equals(snowFlake)) {
           String name = event.getAuthor().getName();
           logger.debug("User '{}' tried to duel themselves.", name);
           channel.sendMessage(String.format("%s is already waiting for a duel!", name)).queue();
       }
        else {

            PlayerHandleUpdater effectiveNames = new PlayerHandleUpdater();
            effectiveNames.updateHandle(current.getPlayer(), lobby.getEffectiveName(current));
            effectiveNames.updateHandle(next.getPlayer(), lobby.getEffectiveName(next));
            List<Player> participants = new ArrayList<>();
            participants.add(current.getPlayer());
            participants.add(next.getPlayer());
            DuelArena session = new DuelArena(effectiveNames);

            session.startDuel(participants);
            lobby.addSession(event.getChannel(), session);
            lobby.clearWaitList(channel);
            channel.sendMessage(String.format("%s has challenged %s to a duel!%n",
                    effectiveNames.getHandle(current.getPlayer()), effectiveNames.getHandle(next.getPlayer()))).queue();
            logger.info("Battle Initiated: Channel: '{}' Players: '{}' , '{}'%n",
                    event.getMessage().getChannel().getName(),
                    effectiveNames.getHandle(participants.get(0)), effectiveNames.getHandle(participants.get(1)));
        }
    }
}
