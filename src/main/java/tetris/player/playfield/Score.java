package tetris.player.playfield;

public class Score {

    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private int scoreAmount;
    private int doubleScore;
    private int amountOfLines;

    public Score() {
        scoreAmount = 0;
        doubleScore = 1;
        amountOfLines = 0;
    }

    public int getScore() {
        return scoreAmount;
    }

    public void setScore(int scoreAmount) {
        this.scoreAmount = scoreAmount;
    }

    public void updateScore(int extraScore, PointsForAbilities points) {
        int previousScore = getScore();
        setScore(previousScore + extraScore);
        amountOfLines++;
        points.addPoints(1);
    }

    public void extraScoreForMultipleLines(int amountOfCompletedLines, PointsForAbilities points) {
        int extraScore = 0;
        if (amountOfCompletedLines == TWO) {
            extraScore = doubleScore * (200 * 2 - 200);
        } else if (amountOfCompletedLines == THREE) {
            extraScore = doubleScore * (300 * 3 - 300);
        } else if (amountOfCompletedLines == FOUR) {
            extraScore = doubleScore * (400 * 4 - 400);
        }
        setScore(getScore() + extraScore);
        points.addPoints(amountOfCompletedLines);
    }

    public void setDoubleScore(int doubleScore) {
        this.doubleScore = doubleScore;
    }

    public int getAmountOfLines() {
        return amountOfLines;
    }

    @Override
    public String toString() {
        return " " + scoreAmount;
    }
}
