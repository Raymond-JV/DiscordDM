package Controller;

import Data.JsonDataParser;
import Game.PlayerSpawner;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DuelClient {

    private final static Logger logger = LogManager.getLogger(DuelClient.class);
    private static String tokenFile = "token.txt";
    private static String itemData = "ItemStats.json";


    public static void main(String[] args) {

        JDA client = null;
        try {
            InputStream in = DuelClient.class.getClassLoader().getResourceAsStream(tokenFile);
            String token = new BufferedReader(new InputStreamReader(in)).readLine();
            client = new JDABuilder(token).build();
        } catch (LoginException e) {
            System.err.println("Error logging in with token.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading token.");
            e.printStackTrace();
        }

        logger.debug("Client successfully created!");

        if (client == null)
            throw new RuntimeException("JDA client failed to load!");
        JsonDataParser parser = new JsonDataParser(itemData);
        DuelLobby lobby = new DuelLobby(client);
        PlayerSpawner spawner = new PlayerSpawner(parser.readWeapons());
        PlayerStatusViewer statusViewer = new PlayerStatusViewer(new StatusPictureTransformer());

        client.addEventListener(new DuelBattleListener(lobby, statusViewer));
        client.addEventListener(new DuelListener(lobby, spawner));

    }
}
