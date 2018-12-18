package game.player.playfields.playfield;

/**
 * @author Remote Access Tetris aka RAT
 */

public class Score {

    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private int scores;

    public Score() {
        scores = 0;
    }


    public int getScore() {
        return scores;
    }

    public void setScore(final int scores) {
        this.scores = scores;
    }

    public void updateScore(final int extraScore) {
        final int previousScore = getScore();
        setScore(previousScore + extraScore);
    }

    public void extraScoreForMultipleLines(final int amountOfCompletedLines) {
        int extraScore = 0;
        if (amountOfCompletedLines == TWO) {
            extraScore = 200 * 2 - 200;
        } else if (amountOfCompletedLines == THREE) {
            extraScore = 300 * 3 - 300;
        } else if (amountOfCompletedLines == FOUR) {
            extraScore = 400 * 4 - 400;
        }
        setScore(getScore() + extraScore);
    }

    @Override
    public String toString() {
        return " " + scores;
    }
}
