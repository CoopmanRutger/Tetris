package game.player.playfield;

import org.junit.Before;
import org.junit.Test;
import tetris.player.playfield.Playfield;
import tetris.player.playfield.Score;
import tetris.player.playfield.block.Block;
import tetris.player.playfield.block.TypesOfBlocks;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayfieldTest {

    private Playfield playfield;
    private Block block;

    @Before
    public void initiate() {
        playfield = new Playfield(20, 12);
        block = new Block(TypesOfBlocks.LBLOCK, "black");
        block.makeBlock(2, 0, 1, 3);
    }

    @Test
    public void testMakeFieldHeight() {
        assertEquals(20, playfield.getPlayfield().size());
    }

    @Test
    public void testMakeFieldWidth() {
        assertEquals(12, playfield.getPlayfield().get(2).size());
    }

    @Test
    public void testAvailability() {
        assertTrue(playfield.positionAvailable(4, 18, block));
    }

    @Test
    public void testPutBlockOnField1() {
        playfield.setCurrentBlock(block);
        playfield.putOnPlayField(4, 2);

        int valueOfPlayfield = playfield.getPlayfield().get(2).get(4);
        assertEquals(0, valueOfPlayfield);
    }

    @Test
    public void testPutBlockOnField2() {
        playfield.setCurrentBlock(block);
        playfield.putOnPlayField(4, 2);

        int valueOfPlayfield1 = playfield.getPlayfield().get(2).get(5);
        assertEquals(0, valueOfPlayfield1);
    }

    @Test
    public void testPutBlockOnField3() {
        playfield.setCurrentBlock(block);
        playfield.putOnPlayField(4, 2);

        int valueOfPlayfield2 = playfield.getPlayfield().get(2).get(6);
        assertEquals(1, valueOfPlayfield2);
    }

    @Test
    public void testPutBlockOnField4() {
        playfield.setCurrentBlock(block);
        playfield.putOnPlayField(4, 2);

        int valueOfPlayfield3 = playfield.getPlayfield().get(3).get(6);
        assertEquals(1, valueOfPlayfield3);
    }

    @Test
    public void testPutBlockOnField5() {
        playfield.setCurrentBlock(block);
        playfield.putOnPlayField(4, 2);

        int valueOfPlayfield4 = playfield.getPlayfield().get(3).get(4);
        assertEquals(1, valueOfPlayfield4);
    }

    @Test
    public void testCheckForCompleted1() {
        List<Integer> completeLine = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            completeLine.add(1);
        }
        playfield.putLineOnField(0, completeLine);
        playfield.putLineOnField(1, completeLine);

        playfield.checkForCompletedLine();

        int valueOnRemovedLine = playfield.getPlayfield().get(1).get(2);
        assertEquals(0, valueOnRemovedLine);
    }

    @Test
    public void testCheckForCompleted2() {
        List<Integer> completeLine = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            completeLine.add(1);
        }
        playfield.putLineOnField(0, completeLine);
        playfield.putLineOnField(1, completeLine);

        playfield.checkForCompletedLine();

        int heightOfPlayingField = playfield.getPlayfield().size();
        assertEquals(20, heightOfPlayingField);
    }

    @Test
    public void testCheckForCompleted3() {
        List<Integer> completeLine = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            completeLine.add(1);
        }
        playfield.putLineOnField(0, completeLine);
        playfield.putLineOnField(1, completeLine);

        playfield.checkForCompletedLine();

        int widthOfPlayingfield1 = playfield.getPlayfield().get(19).size();
        assertEquals(12, widthOfPlayingfield1);
    }

    @Test
    public void testCheckForCompleted4() {
        List<Integer> completeLine = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            completeLine.add(1);
        }
        playfield.putLineOnField(0, completeLine);
        playfield.putLineOnField(1, completeLine);

        playfield.checkForCompletedLine();

        int widthOfPlayingfield2 = playfield.getPlayfield().get(0).size();
        assertEquals(10, widthOfPlayingfield2);
    }

    @Test
    public void testCheckForCompleted5() {
        Score score = new Score();
        List<Integer> completeLine = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            completeLine.add(1);
        }

        playfield.putLineOnField(19, completeLine);
        playfield.putLineOnField(18, completeLine);


        playfield.checkForCompletedLine();

        score.setScore(playfield.getScore());

        assertEquals(400, score.getScore());
    }
}
