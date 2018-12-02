package Game.Battle;

import Utility.CircularLinkedList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TurnKeeper {

    private Player next;
    private Player current;
    private CircularLinkedList<Player> players;
    private Iterator<Player> marker;

    public TurnKeeper(List<Player> players)
    {
        this.players = new CircularLinkedList<>();
        Collections.shuffle(players);
        for (Player p : players)
            this.players.addFront(p);
        marker = this.players.iterator();
        current = marker.next();
        next = marker.next();
    }


    public boolean finished()
    {
        return players.size() == 1;
    }

    public Player cycle()
    {
        current = next;
        next = marker.next();
        return current;
    }

    public void remove(Player p)
    {
        if (next == p) {
            marker.remove();
            next = marker.next();
        }
        else {
            for (int i = 0; i < players.size(); i++)
            {
                if (p == marker.next())
                    marker.remove();
            }
        }
    }

    public Player current()
    {
        return current;
    }
    public Player peek()
    {
        return next;
    }

    public int remaining()
    {
        return players.size();
    }
}
