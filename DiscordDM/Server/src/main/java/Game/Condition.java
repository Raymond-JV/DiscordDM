package Game;

public class Condition {

    private EFFECT status;
    private int duration;

    public Condition(EFFECT status, int duration) {
        this.status = status;
        this.duration = duration;
    }

    public void tick() throws ExpiredConditionException {
        if (duration == 0) {
            String error = String.format("Error, condition %s expired and cannot be applied.", status.name());
            throw new ExpiredConditionException(error);
        } else {
            this.duration--;
        }
    }
}
