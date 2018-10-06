package Game;

public class MessageContext {
    private Account author;
    private String message;

    public MessageContext(Account author, String message) {
        this.author = author;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean verifySender(Account toVerify) {
        return toVerify.getSnowFlakeId() == author.getSnowFlakeId();
    }
}
