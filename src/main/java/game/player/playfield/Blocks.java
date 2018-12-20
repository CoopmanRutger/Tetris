package game.player.playfield;

import game.player.playfield.block.Block;
import game.player.playfield.block.TypesOfBlocks;

import java.util.ArrayList;
import java.util.List;

public class Blocks {

    private List<Block> blocks;

    public Blocks() {
        blocks = new ArrayList<>();
        makeBlocks();
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    Block getBlock() {
        int amountOfBlocks = blocks.size() - 1;
        Long randomNumber = Math.round(Math.random() * amountOfBlocks);
        int randomNumberToInt = randomNumber.intValue();
        return blocks.get(randomNumberToInt);
    }

    private void makeBlocks() {
        final Block lBlock = new Block(TypesOfBlocks.lBlock,
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2"
                + "NkYGBg6Jz34T+Ifv3kGIOojBWYBgFkNlgADfTUeTEygjQLC3Iz3Li8G5sanGIauq5gPYwlTdvAtpMLwC"
                + "6AORlkKjEA2bUYBvDxsYDN+PTpDwOIDaLRAYoByF4AuQCXJmRDCLoAm604DUB3AclhQK4Bb19cYRCW0E"
                + "GNRrLCgBQXvH3/lQE90aEkJGzpAKYJRIMAzACcXiA2ELEaABIkBoACDwbAXoBlDGI0w9TAMxNIgFCGgjk"
                + "X5kKYC0DZGQAfwJNr7nKi7AAAAABJRU5ErkJggg==");
        final Block lineBlock = new Block(TypesOfBlocks.lineBlock,
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2N"
                + "kYGBgKD1y/D+Ifn7sCIOklQ2YBgFkNlgADSwpK2VkBGkW4udjuLp9GzY1OMW0Pb3AehhjurrBtpMLwC6"
                + "AORlkKjEA2bUYBvBzcYPN+PjtKwOIDaLRAYoByF4AuQCXJmRDCLoAm604DUB3AclhQK4Br69fYxDV1EKNR"
                + "rLCgBQXvPv4iQE90aEkJGzpAKYJRIMAzACcXiA2ELEaABIkBoACDwbAXoBlDGI0w9TAMxNIgFCGgjkX5k"
                + "KYC0DZGQBReJAxJHOTqwAAAABJRU5ErkJggg==");
        final Block sblock = new Block(TypesOfBlocks.squareBlock,
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2Nk"
                + "YGBg6Ly04z+IfnXhJoOYgTqYBgFkNlgADfTG5TMygjQL8/EzXD90CpsanGKadmZgPYzFiyaCbScXgF0Ac"
                + "zLIVGIAsmsxDODj4gGb8enbFwYQG0SjAxQDkL0AcgEuTciGEHQBNltxGoDuApLDgFwD3tx+yCCiKo8ajWS"
                + "FASkuePvpIwN6okNJSNjSAUwTiAYBmAE4vUBsIGI1ACRIDAAFHgyAvQDLGMRohqmBZyaQAKEMBXMuzIUwF4"
                + "CyMwBvl5MXVeEacQAAAABJRU5ErkJggg==");
        final Block tBlock = new Block(TypesOfBlocks.tBlock,
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg+"
                + "D8l5T+I3vf0G4OTNBeYBgFkNlgADTi3L2NkBGsWFmfYd+k+NjU4xZz0FMF6GPdWRoFtJxeAXQB3sp4i"
                + "UeYguxbTAH5hiCEf3zIwgNggGg2gGIDsBZC/cGlCNoOwC7DYitMADBcQEQr4vUCkARdefmUwEOdGjUaywoA"
                + "kL7x9yYCe6FASEtgF6ACqiQFEgwA01eL2AiVhADKVGAAKPBgAewGWMYjRDFMDz0wgAUIZCuZfmAthLgBlZwB"
                + "vBonjT09XegAAAABJRU5ErkJggg==");
        final Block zBlock = new Block(TypesOfBlocks.zBlock,
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg2"
                + "Fvy6T+IvvnlGIM6jxWYBgFkNlgADWTN8GBkBGnmE+FiOP1gDzY1OMVMFVzAehinZewA204uALsA5mS"
                + "QqcQAZNdiGMAtwAw24+uHvwwgNohGBygGIHsB5AJcmpANIegCbLbiNADdBSSHAbkGPPh0hUGBTwc1GskKA1"
                + "£Jc8OnNNwb0RIeSkLClA5gmEA0CMANweoHYQMRqAEiQGAAKPBgAewGWMYjRDFMDz0wgAUIZCuZcmAthLgBl"
                + "ZwBQ3ZP3OaGtaAAAAABJRU5ErkJggg==");
        final Block iLblock = new Block(TypesOfBlocks.inverseLBlock,
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg"
                + "eJXf8h9EH/n4ksGGXxxMgwAyGyyABoIWTGZkBGnmExdj2HbrMjY1OMW81HTBehjXJeSCbScXgF0AczLI"
                + "VGIAsmsxDGAXEQSb8fPNewYQG0SjAxQDkL0AcgEuTciGEHQBNltxGoDuApLDgFwDrnx8w6DDL4IajWSFASk"
                + "u+PTyFQN6okNJSNjSAUwTiAYBmAE4vUBsIGI1ACRIDAAFHgyAvQDLGMRohqmBZyaQAKEMBXMuzIUwF4CyMw"
                + "BUFZC9raUyoQAAAABJRU5ErkJggg==");
        final String image = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAw0lEQVQ4"
            + "T2NkYGBg+Hln0n8QfeLSGwYLPREwDQLIbLAAGrAPamJkBGlm4+RjOHTyHjY1OMXszJXAehgPrqsD204uALsA5mSQqcQAZNd"
            + "iGsDOAzHj5xcGBhAbRKMBFAOQvQB2AQ5NyGYQdgEWW3EagOECIgIBvxeINODK7bcMOqrCqNFIVhiQ4oVf3z8xoCc6lISELR3"
            + "ANIFoEIAZgNsLlIQByFRiACjwYADsBVjGIEYzTA08M4EECGUomH9hLoS5AJSdASaukfnTt+kFAAAAAElFTkSuQmCC";
        final Block nBlock = new Block(TypesOfBlocks.leftNBlock, image);
        final Block specialBlock = new Block(TypesOfBlocks.leftNBlock, image);

        lBlock.makeBlock(2, 0, 1, 3);
        lineBlock.makeBlock(0, 0, 4, 0);
        sblock.makeBlock(0, 0, 2, 2);
        tBlock.makeBlock(0, 1, 3, 1);
        zBlock.makeBlock(0, 1, 2, 2);
        iLblock.makeBlock(0, 0, 1, 3);
        nBlock.makeBlock(1, 0, 2, 2);
        specialBlock.makeBlock(1, 0, 2, 4);

        blocks.add(lBlock);
        blocks.add(lineBlock);
        blocks.add(sblock);
        blocks.add(tBlock);
        //        blocks.add(zBlock);
        blocks.add(iLblock);
        //        blocks.add(nBlock);
        //        blocks.add(specialBlock);
    }
}

