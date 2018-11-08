package game.player.playfields.playfield;

import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreTest {

    @Test
    public void testUpdateScore() {
        Score score = new Score();
        score.updateScore();
        assertEquals(100, score.getScore());
        score.updateScore();
        assertEquals(200, score.getScore());
    }

    @Test
    public void testUpdateScoreForMultipleLines() {
        Score score = new Score();
        score.updateScore();
        score.updateScore();
        score.extraScoreForMultipleLines(2);
        assertEquals(400, score.getScore());
    }

}