package game.player.info;

public class Gold {

    private int gold;

    public Gold() {
        gold = 0;
    }

    public int getGold() {
        return gold;
    }


    public void addGold(int gold) {
        this.gold += gold;
    }

    public boolean removeGold(int gold) {
        int remainingGold = this.gold - gold;
        return remainingGold >= 0;
    }

    @Override
    public String toString() {
        return Integer.toString(gold);
    }
}
