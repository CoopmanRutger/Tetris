package game.player.playfields.playfield;

import game.player.playfields.playfield.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Blocks {

    private List<Block> blocks;

    Blocks() {
        blocks = new ArrayList<>();
    }

    Block getBlock() {
        int amountOfBlocks = blocks.size() - 1;
        Long randomNumber = Math.round((Math.random() * amountOfBlocks));
        int randomNumberToInt = randomNumber.intValue();
        return blocks.get(randomNumberToInt);
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void removeBlock(Block block) {
        blocks.remove(block);
    }
}
