package game.player.playfield;

import org.junit.Before;
import org.junit.Test;
import tetris.player.playfield.PointsForAbilities;
import tetris.player.playfield.Score;

import static org.junit.Assert.assertEquals;

public class ScoreTest {

    private Score score;
    private PointsForAbilities points;

    @Before
    public void initiate() {
        score = new Score();
        points = new PointsForAbilities();
    }

    @Test
    public void testUpdateScore1() {
        score.updateScore(10, points);
    }

    @Test
    public void testUpdateScore2() {
        score.updateScore(100, points);
        score.updateScore(100, points);
        assertEquals(200, score.getScore());
    }

    @Test
    public void testUpdateScoreForMultipleLines() {
        score.updateScore(10, points);
        score.updateScore(10, points);
        score.extraScoreForMultipleLines(2, points);
        assertEquals(220, score.getScore());
    }

}
