package game.player.info;

/**
 * @author Remote Access Tetris aka RAT
 */

public class Gold {

    private int money;

    public Gold() {
        money = 0;
    }

    public int getGold() {
        return money;
    }


    public void addGold(final int gold) {
        this.money += gold;
    }

    public boolean removeGold(final int gold) {
        final int remainingGold = this.money - gold;
        return remainingGold >= 0;
    }

    @Override
    public String toString() {
        return Integer.toString(money);
    }
}
