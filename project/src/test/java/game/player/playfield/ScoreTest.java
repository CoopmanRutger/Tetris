package game.player.playfields.playfield;

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
        score.updateScore();
        assertEquals(100, score.getScore());
        score.updateScore();
        assertEquals(200, score.getScore());
    }

    @Test
    public void testUpdateScoreForMultipleLines() {
        score.updateScore();
        score.updateScore();
        score.extraScoreForMultipleLines(2);
        assertEquals(400, score.getScore());
    }

}