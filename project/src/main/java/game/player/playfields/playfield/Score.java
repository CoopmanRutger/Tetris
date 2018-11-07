package game.player.playfields.playfield;

public class Score {

    private int score;

    public Score() {
        score = 0;
    }

    private int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    public void updateScore() {
        int previousScore = getScore();
        int extraScore = 15; // TODO: verschillende puntenmethodes.
        setScore(previousScore + extraScore);
    }

    @Override
    public String toString() {
        return " " + score;
    }
}