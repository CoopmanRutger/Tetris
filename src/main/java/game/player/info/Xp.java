package game.player.info;

public class Xp {

    private int xp;
    private int level;
    private int previousLevelScore;
    private int nextLevelScore;

    public Xp() {
        xp = 0;
        level = 1;
        previousLevelScore = 0;
        nextLevelScore = 300;
    }

    public int getXp() {
        return xp;
    }

    private void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    public void updateXp() {
        setXp(getXp() + 100);
        upgradeLevelIfNecessary();
        // TODO: updatexp oproepen na winnen van wedstrijd.
    }

    private void upgradeLevelIfNecessary() {
        if (getXp() >= nextLevelScore) {
            setLevel(getLevel() + 1);
            previousLevelScore = nextLevelScore;
            nextLevelScore = 2 * previousLevelScore;
        }
    }


}
