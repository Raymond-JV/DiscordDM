package Game;

import Game.Battle.DuelHistory;
import Game.Battle.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RecordedGames {

    //We are checking equality of the object reference
    //There is no need to override hashcode() and equals()
    private Map<Player, DuelHistory> gameHistory = new HashMap<>();

    public RecordedGames()
    {}

    public void addGame(Player player, DuelResult result)
    {
        if (!gameHistory.containsKey(player))
            gameHistory.put(player, new DuelHistory());
        gameHistory.get(player).addDuel(result);
    }

    public DuelHistory getDuelHistory(Player player)
    {
        return gameHistory.get(player);
    }

    public int gamesPlayed()
    {
        int count = 0;

        for (DuelHistory currentHistory : gameHistory.values())
        {
            count +=  currentHistory.getTotalPlayed();
        }
        return count;
    }

}
