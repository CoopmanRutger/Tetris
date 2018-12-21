package tetris.player.info;

public class Xp {

    private int experience;
    private int level;
    private int previousLevelScore;
    private int nextLevelScore;

    public Xp() {
        experience = 0;
        level = 1;
        previousLevelScore = 0;
        nextLevelScore = 300;
    }

    public int getXp() {
        return experience;
    }

    private void setXp(int experience) {
        this.experience = experience;
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
