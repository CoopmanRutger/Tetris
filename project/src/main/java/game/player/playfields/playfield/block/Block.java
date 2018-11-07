package game.player.playfields.playfield.block;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Block {

    private String name;
    private TypesOfBlocks typeOfBlock;
    private Color color;
    private int xValue;
    private int yValue;
    private List<List<Integer>> block;

    public Block(String name, TypesOfBlocks typeOfBlock, Color color) {
        this.name = name;
        this.typeOfBlock = typeOfBlock;
        this.color = color;
        this.xValue = 0;
        this.yValue = 0;
        block = new ArrayList<>();
    }

    public void makeBlock(int startPositionForTopLine, int startPositionForBottomLine,
                          int amountOfBlocksOnTopLine, int amountOfBlocksInBottomLine) {
        makeWidthOrHeight(startPositionForTopLine, amountOfBlocksOnTopLine);
        makeWidthOrHeight(startPositionForBottomLine, amountOfBlocksInBottomLine);
    }

    private void makeWidthOrHeight(int startPos, int amountOfBlocks) {
        List<Integer> blockSize = new ArrayList<>();
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
        block.add(blockSize);
    }

    public List<List<Integer>> getBlock() {
        return Collections.unmodifiableList(block);
    }

    public Color getColor() {
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

        for (int i = block.get(0).size() - 1; i >= 0; i--) {
            blockAfterTurn.add(makeLineForLeftRotation(i));
        }
        block = blockAfterTurn;
    }

    private List<Integer> makeLineForLeftRotation(int i) {
        List<Integer> valuesAfterTurn = new ArrayList<>();
        for (int j = 0; j < block.size(); j++) {
            if (block.get(j).get(i) != 0) {
            valuesAfterTurn.add(1);
            } else {
            valuesAfterTurn.add(0);
            }
        }
        return valuesAfterTurn;
    }

    public void rotateRight() {
        List<List<Integer>> blockAfterTurn = new ArrayList<>();

        for (int i = 0; i < block.get(0).size(); i++) {
            blockAfterTurn.add(makeLineForRightRotation(i));
        }
        block = blockAfterTurn;
    }

    private List<Integer> makeLineForRightRotation(int index) {
        List<Integer> valuesAfterTurn = new ArrayList<>();
        for (int j = block.size() - 1; j >= 0; j--) {
            if (block.get(j).get(index) != 0) {
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
        for (int i = 0; i < block.size(); i++) {
            res.append("{ ");
            for (int j = 0; j < block.get(i).size(); j++) {
                res.append(block.get(i).get(j));
                res.append(", ");
            }
            res.append(" }");
            res.append('\n');
        }
        return res.toString();
    }
}