package Controller;

import Game.Battle.DuelArena;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Invite;

import java.util.HashMap;
import java.util.Map;

public class DuelSession {

    Map<Channel, DuelArena> duels = new HashMap<>();
    public DuelSession(){}
}
