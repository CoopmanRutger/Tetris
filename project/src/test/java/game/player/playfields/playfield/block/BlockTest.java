package game.player.playfields.playfield.block;

import org.junit.Test;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.*;

public class BlockTest {

    @Test
    public void testMakeBlock() {
        Block block = new Block("tblock", TypesOfBlocks.tBlock, Color.BLUE);
        block.makeBlock(1, 0, 1, 3);

        int blockValue = block.getBlock().get(0).get(0);
        int blockValue1 = block.getBlock().get(0).get(1);
        int blockValue2 = block.getBlock().get(0).get(2);
        int blockValue3 = block.getBlock().get(0).get(3);
        assertEquals(0, blockValue);
        assertEquals(1, blockValue1);
        assertEquals(0, blockValue2);
        assertEquals(0, blockValue3);
        int blockValue4 = block.getBlock().get(1).get(0);
        int blockValue5 = block.getBlock().get(1).get(1);
        int blockValue6 = block.getBlock().get(1).get(2);
        int blockValue7 = block.getBlock().get(1).get(3);
        assertEquals(1, blockValue4);
        assertEquals(1, blockValue5);
        assertEquals(1, blockValue6);
        assertEquals(0, blockValue7);
    }

    @Test
    public void testRotateRight() {
        Block block = new Block("inverseLBlock", TypesOfBlocks.inverseLBlock, Color.BLUE);
        block.makeBlock(0, 0, 1, 3);
        block.rotateRight();

        int blockValue = block.getBlock().get(0).get(0);
        int blockValue1 = block.getBlock().get(1).get(0);
        int blockValue2 = block.getBlock().get(2).get(0);
        int blockValue3 = block.getBlock().get(3).get(0);
        assertEquals(1, blockValue);
        assertEquals(1, blockValue1);
        assertEquals(1, blockValue2);
        assertEquals(0, blockValue3);
        int blockValue4 = block.getBlock().get(0).get(1);
        int blockValue5 = block.getBlock().get(1).get(1);
        int blockValue6 = block.getBlock().get(2).get(1);
        int blockValue7 = block.getBlock().get(3).get(1);
        assertEquals(1, blockValue4);
        assertEquals(0, blockValue5);
        assertEquals(0, blockValue6);
        assertEquals(0, blockValue7);
    }

    @Test
    public void testRotateLeft() {
        Block block = new Block("inverseLBlock", TypesOfBlocks.inverseLBlock, Color.BLUE);
        block.makeBlock(0, 0, 1, 3);
        block.rotateLeft();

        int blockValue = block.getBlock().get(0).get(0);
        int blockValue1 = block.getBlock().get(1).get(0);
        int blockValue2 = block.getBlock().get(2).get(0);
        int blockValue3 = block.getBlock().get(3).get(0);
        assertEquals(0, blockValue);
        assertEquals(0, blockValue1);
        assertEquals(0, blockValue2);
        assertEquals(1, blockValue3);
        int blockValue4 = block.getBlock().get(0).get(1);
        int blockValue5 = block.getBlock().get(1).get(1);
        int blockValue6 = block.getBlock().get(2).get(1);
        int blockValue7 = block.getBlock().get(3).get(1);
        assertEquals(0, blockValue4);
        assertEquals(1, blockValue5);
        assertEquals(1, blockValue6);
        assertEquals(1, blockValue7);
    }

}