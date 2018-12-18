package game.player.playfields.playfield.block;

import org.junit.Test;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.*;

public class BlockTest {

    @Test
    public void testMakeBlock() {
        Block block = new Block("TBLOCK", TypesOfBlocks.TBLOCK, Color.BLUE);
        block.makeBlock(1, 0, 1, 3);

        int blockValue = block.getBlock().get(0).get(0);
        assertEquals(0, blockValue);

        int blockValue1 = block.getBlock().get(0).get(1);
        assertEquals(1, blockValue1);

        int blockValue2 = block.getBlock().get(0).get(2);
        assertEquals(0, blockValue2);

        int blockValue3 = block.getBlock().get(0).get(3);
        assertEquals(0, blockValue3);

        int blockValue4 = block.getBlock().get(1).get(0);
        assertEquals(1, blockValue4);

        int blockValue5 = block.getBlock().get(1).get(1);
        assertEquals(1, blockValue5);

        int blockValue6 = block.getBlock().get(1).get(2);
        assertEquals(1, blockValue6);

        int blockValue7 = block.getBlock().get(1).get(3);
        assertEquals(0, blockValue7);
    }

    @Test
    public void testRotateRight() {
        Block block = new Block("INVERSELBLOCK", TypesOfBlocks.INVERSELBLOCK, Color.BLUE);
        block.makeBlock(0, 0, 1, 3);
        block.rotateRight();

        int blockValue = block.getBlock().get(0).get(0);
        assertEquals(1, blockValue);

        int blockValue1 = block.getBlock().get(1).get(0);
        assertEquals(1, blockValue1);

        int blockValue2 = block.getBlock().get(2).get(0);
        assertEquals(1, blockValue2);

        int blockValue3 = block.getBlock().get(3).get(0);
        assertEquals(0, blockValue3);

        int blockValue4 = block.getBlock().get(0).get(1);
        assertEquals(1, blockValue4);

        int blockValue5 = block.getBlock().get(1).get(1);
        assertEquals(0, blockValue5);

        int blockValue6 = block.getBlock().get(2).get(1);
        assertEquals(0, blockValue6);

        int blockValue7 = block.getBlock().get(3).get(1);
        assertEquals(0, blockValue7);
    }

    @Test
    public void testRotateLeft() {
        Block block = new Block("INVERSELBLOCK", TypesOfBlocks.INVERSELBLOCK, Color.BLUE);
        block.makeBlock(0, 0, 1, 3);
        block.rotateLeft();

        int blockValue = block.getBlock().get(0).get(0);
        assertEquals(0, blockValue);

        int blockValue1 = block.getBlock().get(1).get(0);
        assertEquals(0, blockValue1);

        int blockValue2 = block.getBlock().get(2).get(0);
        assertEquals(0, blockValue2);

        int blockValue3 = block.getBlock().get(3).get(0);
        assertEquals(1, blockValue3);

        int blockValue4 = block.getBlock().get(0).get(1);
        assertEquals(0, blockValue4);

        int blockValue5 = block.getBlock().get(1).get(1);
        assertEquals(1, blockValue5);

        int blockValue6 = block.getBlock().get(2).get(1);
        assertEquals(1, blockValue6);

        int blockValue7 = block.getBlock().get(3).get(1);
        assertEquals(1, blockValue7);
    }

}