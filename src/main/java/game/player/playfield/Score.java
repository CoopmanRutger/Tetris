package game.player.playfield;

public class Score {

    private int score;
    private int doubleScore;
    private int amountOfLines;

    public Score() {
        score = 0;
        doubleScore = 1;
        amountOfLines = 0;
    }

    public int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }

    public void updateScore(int extraScore, PointsForAbilities points) {
        int previousScore = getScore();
        setScore(previousScore + extraScore);
        amountOfLines++;
        points.addPoints(1);
    }

    void extraScoreForMultipleLines(int amountOfCompletedLines, PointsForAbilities points) {
        int extraScore = 0;
        if (amountOfCompletedLines == 2) {
            extraScore = doubleScore * (200 * 2 - 200);
        } else if (amountOfCompletedLines == 3) {
            extraScore = doubleScore * (300 * 3 - 300);
        } else if (amountOfCompletedLines == 4) {
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
        return " " + score;
    }
}
