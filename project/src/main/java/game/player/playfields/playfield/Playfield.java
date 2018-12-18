package game.player.playfields.playfield;

import game.player.playfields.playfield.block.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Remote Access Tetris aka RAT
 */

public class Playfield {

    private static final int ONE = 1;
    private static final int TEN = 10;
    // hoogte 20 en breedte 10
    private final List<List<Integer>> playfieldList;
    private Score score;
    private PointsForAbilities points;
    private final Blocks blocks;

    public Playfield() {
        playfieldList = new ArrayList<>();
        score = new Score();
        points = new PointsForAbilities();
        blocks = new Blocks();
        makeStandardPlayfield();
    }

    public void setScore(final Score score) {
        this.score = score;
    }

    public void setPoints(final PointsForAbilities points) {
        this.points = points;
    }

    public void updateScore(final int extraScore) {
        final int previousScore = score.getScore();
        score.setScore(previousScore + extraScore);
    }

    private void makeStandardPlayfield() {
        makePlayfield(20, 10);
    }

    public final void makePlayfield(final int height, final int width) {
        for (int i = 0; i < height; i++) {
            final List<Integer> playWidth = creatNewArrayList();
            for (int j = 0; j < width; j++) {
                playWidth.add(0);
            }
            playfieldList.add(playWidth);
        }
    }

    private static List<Integer> creatNewArrayList() {
        return new ArrayList<>();
    }

    public List<List<Integer>> getPlayfieldList() {
        return Collections.unmodifiableList(playfieldList);
    }

    public void newBlock() {
        final Block randomBlock = blocks.getBlock();

        putOnPlayField(18, 3, randomBlock);
    }

    public void putOnPlayField(final int xPos, final int yPos, final Block randomBlock) {
        if (positionAvailable(xPos, yPos, randomBlock)) {
            final List<List<Integer>> block = randomBlock.getBlock();
            int newYPos = yPos;
            int newXPos = xPos;
            for (int i = block.size() - 1; i >= 0; i--) {
                for (int j = 0; j < block.get(i).size(); j++) {
                    if (block.get(i).get(j) != 0) {
                        playfieldList.get(newXPos).set(newYPos, 1);
                    }
                    newYPos++;
                }
                newYPos = yPos;
                newXPos++;
            }
        }
    }

    public Boolean positionAvailable(final int positionX, final int positionY, final Block block) {
        final List<List<Integer>> controlBlock = block.getBlock();
        int newYPos = positionY;
        int newXPos = positionX;
        for (int i = controlBlock.size() - 1; i >= 0; i--) {
            for (int j = 0; j < controlBlock.get(i).size(); j++) {
                if (controlBlock.get(i).get(j) != 0) {
                    if (playfieldList.get(newXPos).get(newYPos) == 0) {
                        newYPos++;
                    } else {
                        return false;
                    }
                }
            }
            newYPos = positionY;
            newXPos++;
        }
        return true;
    }

    public void checkForCompletedLine() {
        int amountOf1s = 0;
        int amountOfCompletedLines = 0;
        final List<Integer> completedLines = new ArrayList<>();
        for (int i = 0; i < playfieldList.size(); i++) {
            for (int j = 0; j < playfieldList.get(i).size(); j++) {
                if (playfieldList.get(i).get(j) == ONE) {
                    amountOf1s++;
                }
            }
            if (amountOf1s == TEN) {
                scoreForCompletedLine();
                completedLines.add(i);
                amountOfCompletedLines++;
            }
            amountOf1s = 0;
        }

        if (amountOfCompletedLines > ONE) {
            score.extraScoreForMultipleLines(amountOfCompletedLines);
        }

        removeCompletedLines(completedLines);
    }

    private void scoreForCompletedLine() {
        score.updateScore(100);
    }

    private void removeCompletedLines(final List<Integer> completedLines) {
        int deleted = 0;
        for (Integer completedLine : completedLines) {
            int lineToRemove = 0;
            if (deleted > 0) {
                lineToRemove = completedLine - deleted;
            }
            playfieldList.remove(lineToRemove);
            final int heightOfField = playfieldList.size();
            final int widthOfField = playfieldList.get(heightOfField - 1).size();
            playfieldList.add(makeLine(widthOfField));
            deleted++;
        }
    }

    private List<Integer> makeLine(final int size) {
        final List<Integer> blocks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            blocks.add(0);
        }
        return blocks;
    }

    public Score getScore() {
        return score;
    }


    public PointsForAbilities getPoints() {
        return points;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Playfield playfield1 = (Playfield) o;
        return Objects.equals(playfieldList, playfield1.playfieldList)
            && Objects.equals(score, playfield1.score);
    }

    @Override
    public int hashCode() {

        return Objects.hash(playfieldList, score);
    }

    @Override
    public String toString() {
        return "Playfield{" + playfieldList
            + ", score=" + score
            + '}';
    }

    public void putLineOnField(final int line, final List<Integer> completeLine) {
        playfieldList.set(line, completeLine);
    }

}
