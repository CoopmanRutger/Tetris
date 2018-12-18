package game.player.playfields.playfield;

import game.player.playfields.playfield.block.Block;
import game.player.playfields.playfield.block.TypesOfBlocks;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class PlayfieldTest  {

    private Playfield playfield;
    private Block block;

    @Before
    public void initiate() {
        playfield = new Playfield();
        block = new Block("LBLOCK", TypesOfBlocks.LBLOCK, Color.BLACK);
        block.makeBlock(2, 0, 1, 3);
    }

    @Test
    public void testMakeField() {
        assertEquals(20,playfield.getPlayfieldList().size());
        assertEquals(10, playfield.getPlayfieldList().get(2).size());
    }

    @Test
    public void testAvailability() {
        assertTrue(playfield.positionAvailable(18, 4, block));
    }

    @Test
    public void testPutBlockOnField() {
        playfield.putOnPlayField(2, 4, block);

        int valueOfPlayfield = playfield.getPlayfieldList().get(2).get(4);
        assertEquals(1, valueOfPlayfield);

        int valueOfPlayfield1 = playfield.getPlayfieldList().get(2).get(5);
        assertEquals(1, valueOfPlayfield1);

        int valueOfPlayfield2 = playfield.getPlayfieldList().get(2).get(6);
        assertEquals(1, valueOfPlayfield2);

        int valueOfPlayfield3 = playfield.getPlayfieldList().get(3).get(6);
        assertEquals(1, valueOfPlayfield3);

        int valueOfPlayfield4 = playfield.getPlayfieldList().get(3).get(4);
        assertEquals(0, valueOfPlayfield4);
    }

    @Test
    public void testCheckForCompleted() {
        List<Integer> completeLine = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            completeLine.add(1);
        }
        playfield.putLineOnField(0, completeLine);
        playfield.putLineOnField(1, completeLine);

        playfield.checkForCompletedLine();

        int valueOnRemovedLine = playfield.getPlayfieldList().get(0).get(3);
        assertEquals(0, valueOnRemovedLine);

        int heightOfPlayingField = playfield.getPlayfieldList().size();
        assertEquals(20, heightOfPlayingField);

        int widthOfPlayingfield1 = playfield.getPlayfieldList().get(19).size();
        assertEquals(10, widthOfPlayingfield1);

        int widthOfPlayingfield2 = playfield.getPlayfieldList().get(0).size();
        assertEquals(10, widthOfPlayingfield2);

        Score score = playfield.getScore();
        int valueOfScore = score.getScore();
        assertEquals(400, valueOfScore);
    }
}