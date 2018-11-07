package game.player.playfields.playfield;

import game.player.playfields.playfield.block.Block;
import game.player.playfields.playfield.block.TypesOfBlocks;
import org.junit.Test;
import java.awt.*;
import static org.junit.Assert.*;

public class PlayfieldTest  {

    @Test
    public void testMakeField() {
        Playfield playfield = new Playfield();
        assertEquals(playfield.getPlayfield().size(), 20);
        assertEquals(playfield.getPlayfield().get(2).size(), 10);
    }

    @Test
    public void testMakeField1() {
        Playfield playfield = new Playfield();
        assertEquals(playfield.getPlayfield().size(), 20);
        assertEquals(playfield.getPlayfield().get(2).size(), 10);
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
        int valueOfPlayfield1 = playfield.getPlayfield().get(2).get(5);
        int valueOfPlayfield2 = playfield.getPlayfield().get(2).get(6);
        int valueOfPlayfield3 = playfield.getPlayfield().get(3).get(6);
        int valueOfPlayfield4 = playfield.getPlayfield().get(3).get(4);
        assertEquals(1, valueOfPlayfield);
        assertEquals(1, valueOfPlayfield1);
        assertEquals(1, valueOfPlayfield2);
        assertEquals(1, valueOfPlayfield3);
        assertEquals(0, valueOfPlayfield4);
    }
}