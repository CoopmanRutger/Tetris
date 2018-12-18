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

    public void updateScore(int extraScore) {
        int previousScore = getScore();
        setScore(previousScore + extraScore);
    }

    void extraScoreForMultipleLines(int amountOfCompletedLines) {
        int extraScore = 0;
        if (amountOfCompletedLines == 2) {
            extraScore = 200*2-200;
        } else if (amountOfCompletedLines == 3) {
            extraScore = 300*3-300;
        } else if (amountOfCompletedLines == 4) {
            extraScore = 400*4-400;
        }
        setScore(getScore() + extraScore);
    }

    @Override
    public String toString() {
        return " " + score;
    }
}