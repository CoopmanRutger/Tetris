package game.player.playfield;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreTest {

    private Score score;

    @Before
    public void initiate() {
        score = new Score();
    }

    @Test
    public void testUpdateScore() {
        score.updateScore(10);
        assertEquals(100, score.getScore());
        score.updateScore(10);
        assertEquals(200, score.getScore());
    }

    @Test
    public void testUpdateScoreForMultipleLines() {
        score.updateScore(10);
        score.updateScore(10);
        score.extraScoreForMultipleLines(2);
        assertEquals(220, score.getScore());
    }

}