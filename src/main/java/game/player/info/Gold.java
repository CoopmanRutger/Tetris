package game.player.info;

public class Gold {

    private int goldAmount;

    public Gold() {
        goldAmount = 0;
    }

    public int getGold() {
        return goldAmount;
    }


    public void addGold(int goldAmount) {
        this.goldAmount += goldAmount;
    }

    public boolean removeGold(int goldAmount) {
        int remainingGold = this.goldAmount - goldAmount;
        return remainingGold >= 0;
    }

    @Override
    public String toString() {
        return Integer.toString(goldAmount);
    }
}
