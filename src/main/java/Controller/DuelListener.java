package Controller;

import Game.Account;
import Game.Battle.DuelArena;
import Game.Battle.Player;
import Game.Combat.WeaponComponent;
import Game.Item;
import Game.PlayerSpawner;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DuelListener extends ListenerAdapter {

    private final static Logger logger = LogManager.getLogger(DuelListener.class);
    private final DuelLobby lobby;
    private final PlayerSpawner spawner;
    private final DiscordHelper helper;
    private final Player dummy;
    private final PlayerStatusViewer viewer;
    private final BattleSessions sessions;
    private final DiscordHelper parser = new DiscordHelper();

    public DuelListener(DuelLobby lobby, PlayerSpawner spawner, PlayerStatusViewer viewer, BattleSessions sessions) {
        this.lobby = lobby;
        this.spawner = spawner;
        helper = new DiscordHelper();
        dummy = spawner.createFullyUnlocked();
        this.viewer = viewer;
        this.sessions = sessions;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot())
            return;

        String input = parser.parseInput(event);

        if (input == null)
            return;

        logger.debug("Name: {} Message: {}", event.getAuthor().getName(), event.getMessage().getContentDisplay());
        MessageChannel channel = event.getMessage().getChannel();

            logger.debug("Parsing for help menu commands.");
            StringBuilder sb = new StringBuilder();
            switch (input) {

                case "about":
                    channel.sendMessage(String.format("**Author**: Raymond [increase]%n%n" +
                            "This game is in beta. Everything is unlocked. Items and a" +
                            " shop are planned for future release.%n%nEnjoy.")).queue();
                    return;

                case "clear":
                    lobby.clearWaitListAndSession(event.getChannel());
                    logger.info("Waitlist and duel cleared");
                    channel.sendMessage("The waitlist and duel have been cleared.").queue();
                    return;
                case "help":
                    channel.sendMessage(String.format
                            ("**.clear** reset the current duel%n%n**.list** view available attacks%n%n**.dm** start a duel%n%n**.status** display duel info%n%n**.about** author and game info")).queue();
                    return;

                case "list":
                    dummy.getSupplies().getWeapons().sort(Comparator.comparing(Item::getName));
                    for (WeaponComponent weapon : dummy.getSupplies().getWeapons()) {
                        sb.append(helper.bold(weapon.getName())).append("\n");
                        StringBuilder codes = new StringBuilder();
                        for (String code : weapon.getAttackList().keySet()) {
                            codes.append(code).append(", ");
                        }
                        sb.append(codes.toString().substring(0, codes.length() - 2)).append("\n\n");
                    }
                    channel.sendMessage(sb.toString().substring(0, sb.length() - 2)).queue();
                    return;

                case "shop":
                    channel.sendMessage("Shop stub to be added.").queue();
                    return;
                case "status":
                    DuelArena session = lobby.getSession(channel);
                    if (session == null)
                        channel.sendMessage("No duel in progress.").queue();
                    else {
                        try {
                            channel.sendFile(viewer.createStatus(channel, session.getStatus())).queue();
                            channel.sendMessage(String.format("Current turn: **%s**", lobby.getCachedName(session.getCurrentPlayer()))).queue();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }


        DuelArena session = lobby.getSession(channel);
        if (session != null) {
            logger.debug("Battle is already in session. Sending to BattleSessions.");
            sessions.applyCommand(event);
        }

        if (!input.equalsIgnoreCase("dm"))
            return;

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
            channel.sendMessage(String.format("%s is looking to fight!", helper.bold(event.getAuthor().getName()))).queue();
            logger.info("Action: send player to waitlist. Channel: '{}' Name: '{}'",
                    event.getMessage().getChannel().getName(), event.getAuthor().getName());
            return;
        }
        if (next.getSnowFlakeId().equals(snowFlake)) {
            String name = event.getAuthor().getName();
            logger.debug("User '{}' tried to duel themselves.", name);
            channel.sendMessage(String.format("%s is already waiting for a duel.", helper.bold(name))).queue();
        } else {

            List<Player> participants = new ArrayList<>();
            participants.add(current.getPlayer());
            participants.add(next.getPlayer());
            lobby.updateName(current);
            lobby.updateName(next);

            session = new DuelArena(lobby.getNameCache());
            session.startDuel(participants);
            lobby.addSession(event.getChannel(), session);
            lobby.clearWaitList(channel);

            channel.sendMessage(String.format("%s has challenged %s to a duel!%n",
                    helper.bold(lobby.getCachedName(current)), helper.bold(lobby.getCachedName(next)))).queue();
            session.getCurrentPlayer();

            Player first = session.getCurrentPlayer();

            channel.sendMessage(String.format("%s goes first.", helper.bold(lobby.getCachedName(first)))).queue();
            logger.info("Battle Initiated: Channel: '{}' Players: '[{} , {}]'",
                    event.getMessage().getChannel().getName(),
                    lobby.getCachedName(participants.get(0)), lobby.getCachedName(participants.get(1)));
        }
    }
}
