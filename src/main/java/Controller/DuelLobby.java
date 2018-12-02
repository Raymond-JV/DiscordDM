package Controller;

import Game.Account;
import Game.Battle.DuelArena;
import Game.Battle.Player;
import Game.Battle.PlayerHandleUpdater;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.HashMap;
import java.util.Map;

public class DuelLobby {

    private JDA oracle;
    private Map<MessageChannel, DuelArena> sessions = new HashMap<>();
    private Map<String, Account> accounts = new HashMap<>();
    private Map<MessageChannel, Account> waitList = new HashMap<>();
    private PlayerHandleUpdater effectiveNames = new PlayerHandleUpdater();


    public DuelLobby(JDA oracle) {
        this.oracle = oracle;
    }

    public void addSession(MessageChannel room, DuelArena duel) {
        this.sessions.put(room, duel);
    }

    public DuelArena getSession(MessageChannel room) {
        return sessions.get(room);
    }

    public void addAccount(Account user) {
        accounts.put(user.getSnowFlakeId(), user);
    }

    public Account getAccount(String snowflake) {
        return this.accounts.get(snowflake);
    }

    public void addWaiter(MessageChannel room, Account waiter) {
        this.waitList.put(room, waiter);
    }

    public Account getWaiter(MessageChannel room) {
        return waitList.get(room);
    }

    public void clearWaitList(MessageChannel room) {
        this.waitList.remove(room);
    }

    public void clearSession(MessageChannel room) {
        this.sessions.remove(room);
    }

    public void clearWaitListAndSession(MessageChannel room) {
        this.getSession(room);
        this.clearWaitList(room);
        this.clearSession(room);
    }

    private String getEffectiveName(Account user)
    {
        return oracle.getUserById(user.getSnowFlakeId()).getName();
    }

    public void updateName(Account user)
    {
        effectiveNames.updateHandle(user.getPlayer(), this.getEffectiveName(user));
    }

    public PlayerHandleUpdater getNameCache()
    {
        return effectiveNames;
    }

    public String getCachedName(Account user)
    {
        return this.getCachedName(user.getPlayer());
    }

    public String getCachedName(Player user)
    {
        return effectiveNames.getHandle(user);
    }
}
