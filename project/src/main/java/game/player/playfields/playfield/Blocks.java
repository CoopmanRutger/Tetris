package game.player.playfields.playfield;

import game.player.playfields.playfield.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Remote Access Tetris aka RAT
 */

public class Blocks {

    private final List<Block> blocksList;

    public Blocks() {
        blocksList = new ArrayList<>();
    }

    public Block getBlock() {
        final int amountOfBlocks = blocksList.size() - 1;
        final Long randomNumber = Math.round(Math.random() * amountOfBlocks);
        final int randomNumberToInt = randomNumber.intValue();
        return blocksList.get(randomNumberToInt);
    }

    public void addBlock(final Block block) {
        blocksList.add(block);
    }

    public void removeBlock(final Block block) {
        blocksList.remove(block);
    }
}
