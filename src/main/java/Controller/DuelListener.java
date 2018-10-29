package Controller;

import Game.Account;
import Game.Battle.DuelArena;
import Game.Battle.Player;
import Game.Combat.WeaponComponent;
import Game.PlayerSpawner;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DuelListener extends ListenerAdapter {

    private final static Logger logger = LogManager.getLogger(DuelListener.class);
    private final DuelLobby lobby;
    private final PlayerSpawner spawner;
    private final DiscordHelper helper;
    private final Player dummy;

    public DuelListener(DuelLobby lobby, PlayerSpawner spawner) {
        this.lobby = lobby;
        this.spawner = spawner;
        helper = new DiscordHelper();
        dummy = spawner.createFullyUnlocked();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot())
            return;
        DiscordHelper parser = new DiscordHelper();
        String input = parser.parseInput(event);

        if (input == null)
            return;

        logger.debug("Name: {} Message: {}", event.getAuthor().getName(), event.getMessage().getContentDisplay());
        MessageChannel channel = event.getMessage().getChannel();

        if (!input.equalsIgnoreCase("dm")) {
            logger.debug("No !dm prefix. Parsing for commands.");
            StringBuilder sb = new StringBuilder();
            switch (input) {
                case "list":
                    List<WeaponComponent> available = dummy.getSupplies().getWeapons();
                    for (WeaponComponent weapon : available) {
                        sb.append(helper.bold(weapon.getName())).append("\n");
                        StringBuilder codes = new StringBuilder();
                        for (String code : weapon.getAttackList().keySet()) {
                            codes.append(code).append(", ");
                        }
                        sb.append(codes.toString().substring(0, codes.length() - 2)).append("\n\n");
                    }
                    channel.sendMessage(sb.toString().substring(0, sb.length() - 2)).queue();
                    return;
                case "help":
                    channel.sendMessage(String.format("**!list**  view available attacks%n%n**!dm** start a duel%n%n**!about** author and game info")).queue();
                    return;
                case "shop":
                    channel.sendMessage("Shop stub to be added.").queue();
                    return;
                case "about":
                    channel.sendMessage(String.format("**Author**: Raymond [increase]%n%n" +
                            "This game is in beta. Everything is unlocked. Items and a" +
                            " shop are planned for future release.%n%nEnjoy.")).queue();
                    return;
            }
        }

        if (!input.equalsIgnoreCase("dm"))
            return;

        DuelArena session = lobby.getSession(channel);
        if (session != null) {
            logger.debug("Battle is already in session. Ignored Message.");
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
            channel.sendMessage(String.format("%s is looking to fight!", helper.bold(event.getAuthor().getName()))).queue();
            logger.info("Action: send player to waitlist. Channel: '{}' Name: '{}'",
                    event.getMessage().getChannel().getName(), event.getAuthor().getName());
            return;
        }
        if (next.getSnowFlakeId().equals(snowFlake)) {
            String name = event.getAuthor().getName();
            logger.debug("User '{}' tried to duel themselves.", name);
            channel.sendMessage(String.format("%s is already waiting for a duel,", helper.bold(name))).queue();
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
