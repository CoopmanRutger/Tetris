package game.player.playfield.block;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlockTest {

    private static final String BLAUW = "blauw";

    @Test
    public void testMakeBlock() {
        Block block = new Block(TypesOfBlocks.TBLOCK, BLAUW);
        block.makeBlock(1, 0, 1, 3);

        int blockValue = block.getBlock().get(0).get(0);
        assertEquals(0, blockValue);
    }

    @Test
    public void testMakeBlock1() {
        Block block = new Block(TypesOfBlocks.TBLOCK, BLAUW);
        block.makeBlock(1, 0, 1, 3);

        int blockValue1 = block.getBlock().get(0).get(1);
        assertEquals(1, blockValue1);
    }

    @Test
    public void testMakeBlock2() {
        Block block = new Block(TypesOfBlocks.TBLOCK, BLAUW);
        block.makeBlock(1, 0, 1, 3);

        int blockValue2 = block.getBlock().get(0).get(2);
        assertEquals(0, blockValue2);
    }

    @Test
    public void testMakeBlock4() {
        Block block = new Block(TypesOfBlocks.TBLOCK, BLAUW);
        block.makeBlock(1, 0, 1, 3);

        int blockValue4 = block.getBlock().get(1).get(0);
        assertEquals(1, blockValue4);
    }

    @Test
    public void testMakeBlock5() {
        Block block = new Block(TypesOfBlocks.TBLOCK, BLAUW);
        block.makeBlock(1, 0, 1, 3);

        int blockValue5 = block.getBlock().get(1).get(1);
        assertEquals(1, blockValue5);
    }

    @Test
    public void testMakeBlock6() {
        Block block = new Block(TypesOfBlocks.TBLOCK, BLAUW);
        block.makeBlock(1, 0, 1, 3);

        int blockValue6 = block.getBlock().get(1).get(2);
        assertEquals(1, blockValue6);
    }

    @Test
    public void testRotateRight() {
        Block block = new Block(TypesOfBlocks.INVERSELBLOCK, BLAUW);
        block.makeBlock(0, 0, 1, 3);
        block.rotateRight();

        int blockValue = block.getBlock().get(0).get(0);
        assertEquals(1, blockValue);
    }

    @Test
    public void testRotateRight1() {
        Block block = new Block(TypesOfBlocks.INVERSELBLOCK, BLAUW);
        block.makeBlock(0, 0, 1, 3);
        block.rotateRight();

        int blockValue1 = block.getBlock().get(1).get(0);
        assertEquals(1, blockValue1);
    }

    @Test
    public void testRotateRight2() {
        Block block = new Block(TypesOfBlocks.INVERSELBLOCK, BLAUW);
        block.makeBlock(0, 0, 1, 3);
        block.rotateRight();

        int blockValue2 = block.getBlock().get(2).get(0);
        assertEquals(1, blockValue2);
    }

    @Test
    public void testRotateRight4() {
        Block block = new Block(TypesOfBlocks.INVERSELBLOCK, BLAUW);
        block.makeBlock(0, 0, 1, 3);
        block.rotateRight();

        int blockValue4 = block.getBlock().get(0).get(1);
        assertEquals(1, blockValue4);
    }

    @Test
    public void testRotateRight5() {
        Block block = new Block(TypesOfBlocks.INVERSELBLOCK, BLAUW);
        block.makeBlock(0, 0, 1, 3);
        block.rotateRight();

        int blockValue5 = block.getBlock().get(1).get(1);
        assertEquals(0, blockValue5);
    }

    @Test
    public void testRotateRight6() {
        Block block = new Block(TypesOfBlocks.INVERSELBLOCK, BLAUW);
        block.makeBlock(0, 0, 1, 3);
        block.rotateRight();

        int blockValue6 = block.getBlock().get(2).get(1);
        assertEquals(0, blockValue6);
    }

    //    @Test
    //    public void testRotateLeft() {
    //        Block block = new Block(TypesOfBlocks.inverseLBlock, BLAUW);
    //        block.makeBlock(0, 0, 1, 3);
    //        block.rotateLeft();
    //
    //        int blockValue = block.getBlock().get(0).get(0);
    //        assertEquals(0, blockValue);
    //
    //        int blockValue1 = block.getBlock().get(1).get(0);
    //        assertEquals(0, blockValue1);
    //
    //        int blockValue2 = block.getBlock().get(2).get(0);
    //        assertEquals(0, blockValue2);
    //
    //        int blockValue3 = block.getBlock().get(3).get(0);
    //        assertEquals(1, blockValue3);
    //
    //        int blockValue4 = block.getBlock().get(0).get(1);
    //        assertEquals(0, blockValue4);
    //
    //        int blockValue5 = block.getBlock().get(1).get(1);
    //        assertEquals(1, blockValue5);
    //
    //        int blockValue6 = block.getBlock().get(2).get(1);
    //        assertEquals(1, blockValue6);
    //
    //        int blockValue7 = block.getBlock().get(3).get(1);
    //        assertEquals(1, blockValue7);
    //    }

}
