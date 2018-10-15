package Game;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String error) {
        super(error);
    }
}
