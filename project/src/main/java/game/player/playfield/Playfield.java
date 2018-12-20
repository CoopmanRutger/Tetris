package game.player.playfield;

import game.player.playfield.block.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Playfield {

    private List<List<Integer>> playfield; // hoogte 20 en breedte 12
    private Score score;
    private PointsForAbilities points;
    private Blocks blocks;
    private Block currentBlock;


    public Block getCurrentBlock() {
        return currentBlock;
    }
    public Blocks getBlocks() {
        return blocks;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public void setPoints(PointsForAbilities points) {
        this.points = points;
    }

    public void updateScore(int extraScore) {
        int previousScore = score.getScore();
        score.setScore(previousScore + extraScore);
    }


    public Playfield(int height, int width) {
        playfield = new ArrayList<>();
        score = new Score();
        points = new PointsForAbilities();
        blocks = new Blocks();
        makeStandardPlayfield(height,width);
    }

    public Playfield(List<List<Integer>> playfield) {
        this.playfield = new ArrayList<>(playfield);
        score = new Score();
        points = new PointsForAbilities();
        blocks = new Blocks();
    }

    public void setPlayfield(List<List<Integer>> playfield) {
        this.playfield = playfield;
    }

    private void makeStandardPlayfield(int height, int width) {
        makePlayfield(height, width);
    }

    public void makePlayfield(int height, int width) {
        for (int i = 0; i < height; i++) {
            List<Integer> playWidth = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                playWidth.add(0);
            }
            playfield.add(playWidth);
        }
    }

    public List<List<Integer>> getPlayfield() {
        return Collections.unmodifiableList(playfield);
    }

    public Block newBlock() {
        currentBlock = new Block(blocks.getBlock());
        System.out.println(currentBlock);
        return getCurrentBlock();
    }

    public boolean putOnPlayField(int xPos, int yPos) {
        boolean putItOnField = false;
        System.out.println("block" + currentBlock);
        if (positionAvailable(xPos, yPos, currentBlock)) {
            putItOnField = positionAvailable(xPos, yPos, currentBlock);
            System.out.println("avaible YES");
            List<List<Integer>> block = currentBlock.getBlock();
            int newYPos = yPos;
            int newXPos = xPos;
            for (int i = 0; i <= block.size() - 1; i++) {
                for (int j = 0; j < block.get(i).size(); j++) {
                    if (block.get(i).get(j) != 0) {
                        playfield.get(newYPos).set(newXPos, 1);
                    }
                    newXPos++;
                }
                newXPos = xPos;
                newYPos++;
            }
        }
        System.out.println("avaible?");
        checkForCompletedLine();
        return putItOnField;

    }

    public Boolean positionAvailable(int positionX, int positionY, Block block) {
        List<List<Integer>> controlBlock = block.getBlock();
        int newYPos = positionY;
        int newXPos = positionX;
        for (int i = controlBlock.size() - 1; i >= 0; i--) {
            for (int j = 0; j < controlBlock.get(i).size(); j++) {
                if (controlBlock.get(i).get(j) != 0) {
                    if (playfield.get(newYPos).get(newXPos) == 0) {
                        newXPos++;
                    } else {
                        return false;
                    }
                }
            }
            newXPos = positionX;
            newYPos++;
        }
        return true;
    }

    public void checkForCompletedLine() {
        int amountOf1s = 0;
        int amountOfCompletedLines = 0;
        int completedLine = -1;
        for (int i = 0; i < playfield.size(); i++) {
            for (int j = 0; j < playfield.get(i).size(); j++) {
                if (playfield.get(i).get(j) == 1) {
                    amountOf1s++;
                }
            }
            System.out.println("width  " + (playfield.get(10).size()) + " ->  " + amountOf1s );
            if (amountOf1s == playfield.get(10).size()) {
                System.out.println("completedLines " + amountOf1s);
                scoreForCompletedLine();
                completedLine = i;
                amountOfCompletedLines++;
                removeCompletedLine(completedLine);
            }
            amountOf1s = 0;
        }

        if (amountOfCompletedLines > 1) {
            score.extraScoreForMultipleLines(amountOfCompletedLines);
        }

    }

    private void scoreForCompletedLine() {
        score.updateScore(100);
    }

    private void removeCompletedLine(int completedLine) {

        System.out.println("1lijn: " + completedLine);
        playfield.remove(completedLine);
        int widthOfField = playfield.get(10).size();
        playfield.add(1, makeLine(widthOfField));
    }

    private List<Integer> makeLine(int size) {
        List<Integer> blocks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            blocks.add(0);
        }
        System.out.println(blocks);
        return blocks;
    }

    void putLineOnField(int line, List<Integer> completeLine) {
        playfield.set(line, completeLine);
    }

    Score getScore() {
        return score;
    }


    public PointsForAbilities getPoints() {
        return points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playfield playfield1 = (Playfield) o;
        return Objects.equals(playfield, playfield1.playfield) &&
                Objects.equals(score, playfield1.score);
    }

    @Override
    public int hashCode() {

        return Objects.hash(playfield, score);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("playfield ")
                .append("\n");
        int number = 0;
        for (List<Integer> list: playfield){
            stringBuilder.append(number).append(list).append("\n");
            number++;
        }

        return stringBuilder.toString();
    }
}