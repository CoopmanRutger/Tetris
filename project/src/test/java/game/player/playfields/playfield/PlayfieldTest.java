package game.player.playfields.playfield;

import game.player.playfields.playfield.block.Block;
import game.player.playfields.playfield.block.TypesOfBlocks;
import org.junit.Test;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class PlayfieldTest  {

    @Test
    public void testMakeField() {
        Playfield playfield = new Playfield();
        assertEquals(20,playfield.getPlayfield().size());
        assertEquals(10, playfield.getPlayfield().get(2).size());
    }

    @Test
    public void testAvailability() {
        Playfield playfield = new Playfield();
        Block block = new Block("Lblock", TypesOfBlocks.lBlock, Color.BLACK);
        block.makeBlock(2, 0, 1, 3);
        assertTrue(playfield.positionAvailable(18, 4, block));
    }

    @Test
    public void testPutBlockOnField() {
        Playfield playfield = new Playfield();
        Block block = new Block("Lblock", TypesOfBlocks.lBlock, Color.BLACK);
        block.makeBlock(2, 0, 1, 3);
        playfield.putOnPlayField(2, 4, block);

        int valueOfPlayfield = playfield.getPlayfield().get(2).get(4);
        assertEquals(1, valueOfPlayfield);

        int valueOfPlayfield1 = playfield.getPlayfield().get(2).get(5);
        assertEquals(1, valueOfPlayfield1);

        int valueOfPlayfield2 = playfield.getPlayfield().get(2).get(6);
        assertEquals(1, valueOfPlayfield2);

        int valueOfPlayfield3 = playfield.getPlayfield().get(3).get(6);
        assertEquals(1, valueOfPlayfield3);

        int valueOfPlayfield4 = playfield.getPlayfield().get(3).get(4);
        assertEquals(0, valueOfPlayfield4);
    }

    @Test
    public void testCheckForCompleted() {
        Playfield playfield = new Playfield();
        List<Integer> completeLine = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            completeLine.add(1);
        }
        playfield.putLineOnField(0, completeLine);
        playfield.putLineOnField(1, completeLine);

        playfield.checkForCompletedLine();

        int valueOnRemovedLine = playfield.getPlayfield().get(0).get(3);
        assertEquals(0, valueOnRemovedLine);

        int heightOfPlayingField = playfield.getPlayfield().size();
        assertEquals(20, heightOfPlayingField);

        int widthOfPlayingfield1 = playfield.getPlayfield().get(19).size();
        assertEquals(10, widthOfPlayingfield1);

        int widthOfPlayingfield2 = playfield.getPlayfield().get(0).size();
        assertEquals(10, widthOfPlayingfield2);

        Score score = playfield.getScore();
        int valueOfScore = score.getScore();
        assertEquals(400, valueOfScore);
    }
}