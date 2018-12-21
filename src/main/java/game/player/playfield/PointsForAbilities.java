package game.player.playfield;

public class PointsForAbilities {

    private int points;

    public PointsForAbilities() {
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        if (points > 0) {
            this.points += points;
        }
    }

    public void removePoints(int points) {
        if (points > 0) {
            this.points -= points;
        }
    }

    public void removeAllPoints() {
        points = 0;
    }

    @Override
    public String toString() {
        return " " + points;
    }
}
