package Game.Battle;

import java.util.HashMap;
import java.util.Map;

public class PlayerHandleUpdater {

    private Map<Player, String> handles = new HashMap<>();

    public PlayerHandleUpdater() {}

    public void updateHandle(Player user, String name){
        handles.put(user, name);
    }

    public String getHandle(Player user)
    {
        return handles.get(user);
    }

}
