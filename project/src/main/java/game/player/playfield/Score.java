package game.player.playfield;

public class Score {

    private int score;

    public Score() {
        score = 0;
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
        points.addPoints(1);
    }

    void extraScoreForMultipleLines(int amountOfCompletedLines, PointsForAbilities points) {
        int extraScore = 0;
        if (amountOfCompletedLines == 2) {
            extraScore = 200*2-200;
        } else if (amountOfCompletedLines == 3) {
            extraScore = 300*3-300;
        } else if (amountOfCompletedLines == 4) {
            extraScore = 400*4-400;
        }
        setScore(getScore() + extraScore);
        points.addPoints(amountOfCompletedLines);
    }

    @Override
    public String toString() {
        return " " + score;
    }
}