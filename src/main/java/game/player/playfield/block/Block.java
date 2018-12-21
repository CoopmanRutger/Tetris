package game.player.playfield.block;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Block {


    private static final String BRACE = " }";
    private TypesOfBlocks typeOfBlock;
    private String color;
    private int xValue;
    private int yValue;
    private List<List<Integer>> blockList;

    public Block(List<List<Integer>> blockList) {
        this.blockList = blockList;
    }

    public Block(TypesOfBlocks typeOfBlock, String color) {
        this.typeOfBlock = typeOfBlock;
        this.color = color;
        this.xValue = 0;
        this.yValue = 0;
        blockList = new ArrayList<>();
    }

    public Block(Block newblock) {
        this.typeOfBlock = newblock.typeOfBlock;
        this.xValue = 0;
        this.yValue = 0;
        this.color = newblock.color;
        this.blockList = new ArrayList<>(newblock.getBlock());
    }

    public void makeBlock(int startPositionForTopLine, int startPositionForBottomLine,
                          int amountOfBlocksOnTopLine, int amountOfBlocksInBottomLine) {
        int amount = 0;
        if (amountOfBlocksInBottomLine > amountOfBlocksOnTopLine) {
            amount = amountOfBlocksInBottomLine;

        } else if (amountOfBlocksInBottomLine == amountOfBlocksOnTopLine) {
            if (startPositionForTopLine != startPositionForBottomLine) {
                amount = amountOfBlocksInBottomLine + 1;
            } else {
                amount = amountOfBlocksInBottomLine;
            }
        } else {
            amount = amountOfBlocksOnTopLine;
        }
        makeWidthOrHeight(startPositionForTopLine, amountOfBlocksOnTopLine, amount);
        if (amountOfBlocksInBottomLine > 0) {
            makeWidthOrHeight(startPositionForBottomLine, amountOfBlocksInBottomLine, amount);
        }
    }

    private void makeWidthOrHeight(int startPos, int amountOfBlocks, int maxAmount) {
        List<Integer> blockSize = new ArrayList<>();
        int indexWidth = 0;
        for (int j = 0; j < maxAmount; j++) {
            if (indexWidth < amountOfBlocks) {
                if (j < startPos) {
                    blockSize.add(0);
                } else {
                    blockSize.add(1);
                    indexWidth++;
                }
            } else {
                blockSize.add(0);
            }
        }
        blockList.add(blockSize);
    }

    public List<List<Integer>> getBlock() {
        return Collections.unmodifiableList(blockList);
    }

    public String getColor() {
        return color;
    }

    public int getXValue() {
        return xValue;
    }

    public void setXValue(int xValue) {
        this.xValue = xValue;
    }

    public int getYValue() {
        return yValue;
    }

    public void setYValue(int yValue) {
        this.yValue = yValue;
    }

    public void rotateLeft() {
        List<List<Integer>> blockAfterTurn = new ArrayList<>();

        for (int i = blockList.get(0).size() - 1; i >= 0; i--) {
            blockAfterTurn.add(makeLineForLeftRotation(i));
        }
        blockList = blockAfterTurn;
    }

    private List<Integer> makeLineForLeftRotation(int i) {
        List<Integer> valuesAfterTurn = new ArrayList<>();
        for (List<Integer> aBlock : blockList) {
            if (aBlock.get(i) != 0) {
                valuesAfterTurn.add(1);
            } else {
                valuesAfterTurn.add(0);
            }
        }
        return valuesAfterTurn;
    }

    public void rotateRight() {
        List<List<Integer>> blockAfterTurn = new ArrayList<>();

        for (int i = 0; i < blockList.get(0).size(); i++) {
            blockAfterTurn.add(makeLineForRightRotation(i));
        }
        blockList = blockAfterTurn;
    }

    private List<Integer> makeLineForRightRotation(int index) {
        List<Integer> valuesAfterTurn = new ArrayList<>();
        for (int j = blockList.size() - 1; j >= 0; j--) {
            if (blockList.get(j).get(index) != 0) {
                valuesAfterTurn.add(1);
            } else {
                valuesAfterTurn.add(0);
            }
        }
        return valuesAfterTurn;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (List<Integer> aBlock : blockList) {
            res.append("{ ");
            for (Integer anABlock : aBlock) {
                res.append(anABlock);
                res.append(", ");
            }
            res.append(BRACE).append('\n');
        }
        return res.toString();
    }
}
