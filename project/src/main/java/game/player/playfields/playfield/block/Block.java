package game.player.playfields.playfield.block;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Remote Access Tetris aka RAT
 */

public class Block {

    private static final String BRACE = " }";
    private final String name;
    private final TypesOfBlocks typeOfBlock;
    private final Color color;
    private int xValue;
    private int yValue;
    private List<List<Integer>> blockList;

    public Block(final String name, final TypesOfBlocks typeOfBlock, final Color color) {
        this.name = name;
        this.typeOfBlock = typeOfBlock;
        this.color = color;
        this.xValue = 0;
        this.yValue = 0;
        blockList = new ArrayList<>();
    }

    public void makeBlock(final int startPositionForTopLine, final int startPositionForBottomLine,
                          final int amountOfBlocksOnTopLine, final int amountOfBlocksInBottomLine) {
        makeWidthOrHeight(startPositionForTopLine, amountOfBlocksOnTopLine);
        makeWidthOrHeight(startPositionForBottomLine, amountOfBlocksInBottomLine);
    }

    private void makeWidthOrHeight(final int startPos, final int amountOfBlocks) {
        final List<Integer> blockSize = new ArrayList<>();
        int indexWidth = 0;
        for (int j = 0; j < 4; j++) {
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

    public Color getColor() {
        return color;
    }

    public int getXValue() {
        return xValue;
    }

    public void setXValue(final int xValue) {
        this.xValue = xValue;
    }

    public int getYValue() {
        return yValue;
    }

    public void setYValue(final int yValue) {
        this.yValue = yValue;
    }

    public String getName() {
        return name;
    }

    public TypesOfBlocks getTypeOfBlock() {
        return typeOfBlock;
    }

    public void rotateLeft() {
        final List<List<Integer>> blockAfterTurn = new ArrayList<>();

        for (int i = blockList.get(0).size() - 1; i >= 0; i--) {
            blockAfterTurn.add(makeLineForLeftRotation(i));
        }
        blockList = blockAfterTurn;
    }

    private List<Integer> makeLineForLeftRotation(final int i) {
        final List<Integer> valuesAfterTurn = new ArrayList<>();
        for (List<Integer> aBlockList : blockList) {
            if (aBlockList.get(i) != 0) {
                valuesAfterTurn.add(1);
            } else {
                valuesAfterTurn.add(0);
            }
        }
        return valuesAfterTurn;
    }

    public void rotateRight() {
        final List<List<Integer>> blockAfterTurn = new ArrayList<>();

        for (int i = 0; i < blockList.get(0).size(); i++) {
            blockAfterTurn.add(makeLineForRightRotation(i));
        }
        blockList = blockAfterTurn;
    }

    private List<Integer> makeLineForRightRotation(final int index) {
        final List<Integer> valuesAfterTurn = new ArrayList<>();
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
        final StringBuilder res = new StringBuilder();
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
