package game.player.playfields.playfield;

/**
 * @author Remote Access Tetris aka RAT
 */

public class PointsForAbilities {

    private int points;

    public PointsForAbilities() {
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(final int points) {
        if (points > 0) {
            this.points += points;
        }
    }

    public void removePoints(final int points) {
        if (points > 0) {
            this.points -= points;
        }
    }

    @Override
    public String toString() {
        return " " + points;
    }
}
