package Game;

import Game.Battle.Player;

public class MessageContext {

    private final Player author;
    private final String message;

    public MessageContext(Player author, String message) {
        this.author = author;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean verifySender(Player toVerify) {
        return this.author == toVerify;
    }
}
