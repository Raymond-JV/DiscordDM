package Game;


public class Account {

    private DuelHistory recordedGames = new DuelHistory();
    private String snowFlakeId = "coffee"; //#TEMPERARY PLACEHOLDER
    private String userName;
    private Supplies unlocks = new Supplies();

    //temp constructor

    public Account(String userName) {
        this.userName = userName;
    }

    public String getSnowFlakeId() {
        return this.snowFlakeId;
    }

    public String getUserName() {
        return this.userName;
    }

    public Supplies getUnlocks() {
        return this.unlocks;
    }

    public DuelHistory getRecordedGames() {
        return recordedGames;
    }

    @Override
    public String toString() {
        return String.format("Account: SnowflakeId: %s, Username: %s", snowFlakeId, userName);
    }


}
