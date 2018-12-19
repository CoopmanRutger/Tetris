package game.player.playfield;

import game.player.playfield.block.Block;
import game.player.playfield.block.TypesOfBlocks;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Blocks {

    private List<Block> blocks;
    private Block currentBlock;


    public Block getCurrentBlock() {
        return currentBlock;
    }

    Blocks() {
        blocks = new ArrayList<>();
        MakeBlocks();
    }

    Block getBlock() {
        int amountOfBlocks = blocks.size() - 1;
        Long randomNumber = Math.round((Math.random() * amountOfBlocks));
        int randomNumberToInt = randomNumber.intValue();
        currentBlock = blocks.get(randomNumberToInt);
        return new Block(currentBlock);
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void removeBlock(Block block) {
        blocks.remove(block);
    }


    private void MakeBlocks(){
        Block Lblock = new Block(TypesOfBlocks.lBlock,"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg6Jz34T+Ifv3kGIOojBWYBgFkNlgADfTUeTEygjQLC3Iz3Li8G5sanGIauq5gPYwlTdvAtpMLwC6AORlkKjEA2bUYBvDxsYDN+PTpDwOIDaLRAYoByF4AuQCXJmRDCLoAm604DUB3AclhQK4Bb19cYRCW0EGNRrLCgBQXvH3/lQE90aEkJGzpAKYJRIMAzACcXiA2ELEaABIkBoACDwbAXoBlDGI0w9TAMxNIgFCGgjkX5kKYC0DZGQAfwJNr7nKi7AAAAABJRU5ErkJggg==");
        Block LineBlock = new Block(TypesOfBlocks.lineBlock,"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBgKD1y/D+Ifn7sCIOklQ2YBgFkNlgADSwpK2VkBGkW4udjuLp9GzY1OMW0Pb3AehhjurrBtpMLwC6AORlkKjEA2bUYBvBzcYPN+PjtKwOIDaLRAYoByF4AuQCXJmRDCLoAm604DUB3AclhQK4Br69fYxDV1EKNRrLCgBQXvPv4iQE90aEkJGzpAKYJRIMAzACcXiA2ELEaABIkBoACDwbAXoBlDGI0w9TAMxNIgFCGgjkX5kKYC0DZGQBReJAxJHOTqwAAAABJRU5ErkJggg==");
        Block Sblok = new Block(TypesOfBlocks.squareBlock,"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg6Ly04z+IfnXhJoOYgTqYBgFkNlgADfTG5TMygjQL8/EzXD90CpsanGKadmZgPYzFiyaCbScXgF0AczLIVGIAsmsxDODj4gGb8enbFwYQG0SjAxQDkL0AcgEuTciGEHQBNltxGoDuApLDgFwD3tx+yCCiKo8ajWSFASkuePvpIwN6okNJSNjSAUwTiAYBmAE4vUBsIGI1ACRIDAAFHgyAvQDLGMRohqmBZyaQAKEMBXMuzIUwF4CyMwBvl5MXVeEacQAAAABJRU5ErkJggg==");
        Block Tblok = new Block(TypesOfBlocks.tBlock,"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg+D8l5T+I3vf0G4OTNBeYBgFkNlgADTi3L2NkBGsWFmfYd+k+NjU4xZz0FMF6GPdWRoFtJxeAXQB3sp4iUeYguxbTAH5hiCEf3zIwgNggGg2gGIDsBZC/cGlCNoOwC7DYitMADBcQEQr4vUCkARdefmUwEOdGjUaywoAkL7x9yYCe6FASEtgF6ACqiQFEgwA01eL2AiVhADKVGAAKPBgAewGWMYjRDFMDz0wgAUIZCuZfmAthLgBlZwBvBonjT09XegAAAABJRU5ErkJggg==");
        Block Zblok = new Block(TypesOfBlocks.zBlock,"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBg2Fvy6T+IvvnlGIM6jxWYBgFkNlgADWTN8GBkBGnmE+FiOP1gDzY1OMVMFVzAehinZewA204uALsA5mSQqcQAZNdiGMAtwAw24+uHvwwgNohGBygGIHsB5AJcmpANIegCbLbiNADdBSSHAbkGPPh0hUGBTwc1GskKA1Jc8OnNNwb0RIeSkLClA5gmEA0CMANweoHYQMRqAEiQGAAKPBgAewGWMYjRDFMDz0wgAUIZCuZcmAthLgBlZwBQ3ZP3OaGtaAAAAABJRU5ErkJggg==");
        Block ILblock= new Block(TypesOfBlocks.inverseLBlock,"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAxElEQVQ4T2NkYGBgeJXf8h9EH/n4ksGGXxxMgwAyGyyABoIWTGZkBGnmExdj2HbrMjY1OMW81HTBehjXJeSCbScXgF0AczLIVGIAsmsxDGAXEQSb8fPNewYQG0SjAxQDkL0AcgEuTciGEHQBNltxGoDuApLDgFwDrnx8w6DDL4IajWSFASku+PTyFQN6okNJSNjSAUwTiAYBmAE4vUBsIGI1ACRIDAAFHgyAvQDLGMRohqmBZyaQAKEMBXMuzIUwF4CyMwBUFZC9raUyoQAAAABJRU5ErkJggg==");
        Block Nblock = new Block(TypesOfBlocks.leftNBlock,"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAw0lEQVQ4T2NkYGBg+Hln0n8QfeLSGwYLPREwDQLIbLAAGrAPamJkBGlm4+RjOHTyHjY1OMXszJXAehgPrqsD204uALsA5mSQqcQAZNdiGsDOAzHj5xcGBhAbRKMBFAOQvQB2AQ5NyGYQdgEWW3EagOECIgIBvxeINODK7bcMOqrCqNFIVhiQ4oVf3z8xoCc6lISELR3ANIFoEIAZgNsLlIQByFRiACjwYADsBVjGIEYzTA08M4EECGUomH9hLoS5AJSdASaukfnTt+kFAAAAAElFTkSuQmCC");
        Block specialBock = new Block(TypesOfBlocks.leftNBlock,"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAw0lEQVQ4T2NkYGBg+Hln0n8QfeLSGwYLPREwDQLIbLAAGrAPamJkBGlm4+RjOHTyHjY1OMXszJXAehgPrqsD204uALsA5mSQqcQAZNdiGsDOAzHj5xcGBhAbRKMBFAOQvQB2AQ5NyGYQdgEWW3EagOECIgIBvxeINODK7bcMOqrCqNFIVhiQ4oVf3z8xoCc6lISELR3ANIFoEIAZgNsLlIQByFRiACjwYADsBVjGIEYzTA08M4EECGUomH9hLoS5AJSdASaukfnTt+kFAAAAAElFTkSuQmCC");

        Lblock.makeBlock(2,0,1,3);
        LineBlock.makeBlock(0,0,4,0);
        Sblok.makeBlock(0,0,2,2);
        Tblok.makeBlock(0,1,3,1);
        Zblok.makeBlock(0,1,2,2);
        ILblock.makeBlock(0,0,1,3);
        Nblock.makeBlock(1,0,2,2);
        specialBock.makeBlock(1,0,2,4);

        blocks.add(Lblock);
        blocks.add(LineBlock);
        blocks.add(Sblok);
        blocks.add(Tblok);
        blocks.add(Zblok);
        blocks.add(ILblock);
        blocks.add(Nblock);
        blocks.add(specialBock);
    }
}

