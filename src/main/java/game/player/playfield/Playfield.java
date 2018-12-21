package game.player.playfield;

import game.player.playfield.block.Block;
import org.pmw.tinylog.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Playfield {

    private static final String NEWLINE = "\n";
    private static final Integer ONE = 1;
    // hoogte 20 en breedte 12
    private List<List<Integer>> playfieldList;
    private Score score;
    private PointsForAbilities points;
    private Blocks blocks;
    private Block currentBlock;
    private int counter;
    private int gameSpeed;
    private boolean blinded;

    public Playfield(int height, int width) {
        playfieldList = new ArrayList<>();
        score = new Score();
        points = new PointsForAbilities();
        blocks = new Blocks();
        counter = 0;
        gameSpeed = 50;
        blinded = false;
        makeStandardPlayfield(height, width);
    }

    public Playfield(List<List<Integer>> playfieldList) {
        this.playfieldList = new ArrayList<>(playfieldList);
        score = new Score();
        points = new PointsForAbilities();
        blocks = new Blocks();
        counter = 0;
        gameSpeed = 50;
    }

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

    public void setPlayfield(List<List<Integer>> playfieldList) {
        this.playfieldList = playfieldList;
    }

    private void makeStandardPlayfield(int height, int width) {
        makePlayfield(height, width);
    }

    public void makePlayfield(int height, int width) {
        for (int i = 0; i < height; i++) {
            List<Integer> playWidth = createNewArraylist();
            for (int j = 0; j < width; j++) {
                playWidth.add(0);
            }
            playfieldList.add(playWidth);
        }
    }

    private List<Integer> createNewArraylist() {
        return new ArrayList<>();
    }

    public List<List<Integer>> getPlayfield() {
        return Collections.unmodifiableList(playfieldList);
    }

    public Block newBlock() {
        counter++;
        currentBlock = new Block(blocks.getBlock());
        Logger.info(currentBlock);
        return getCurrentBlock();
    }

    public void putOnPlayField(int xPos, int yPos) {
        //boolean putItOnField = false;
        Logger.info("block" + currentBlock);
        if (positionAvailable(xPos, yPos, currentBlock)) {
            //putItOnField = positionAvailable(xPos, yPos, currentBlock);
            Logger.info("avaible YES");
            List<List<Integer>> block = currentBlock.getBlock();
            int newYPos = yPos;
            int newXPos = xPos;
            for (List<Integer> integer : block) {
                for (Integer anInteger : integer) {
                    if (anInteger != 0) {
                        playfieldList.get(newYPos).set(newXPos, 1);
                    }
                    newXPos++;
                }
                newXPos = xPos;
                newYPos++;
            }
        }
        Logger.warn("avaible?");
        checkForCompletedLine();

    }


    public Boolean positionAvailable(int positionX, int positionY, Block block) {
        List<List<Integer>> controlBlock = block.getBlock();
        int newYPos = positionY;
        int newXPos = positionX;
        for (int i = controlBlock.size() - 1; i >= 0; i--) {
            for (int j = 0; j < controlBlock.get(i).size(); j++) {
                if (controlBlock.get(i).get(j) != 0) {
                    if (playfieldList.get(newYPos).get(newXPos) == 0) {
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
        for (int i = 0; i < playfieldList.size(); i++) {
            for (int j = 0; j < playfieldList.get(i).size(); j++) {
                if (playfieldList.get(i).get(j).equals(ONE)) {
                    amountOf1s++;
                }
            }
            if (amountOf1s == playfieldList.get(10).size()) {
                scoreForCompletedLine();
                completedLine = i;
                amountOfCompletedLines++;
                removeCompletedLine(completedLine);
            }
            amountOf1s = 0;
        }

        if (amountOfCompletedLines > ONE) {
            score.extraScoreForMultipleLines(amountOfCompletedLines, points);
        }

    }

    private void scoreForCompletedLine() {
        score.updateScore(100, points);
    }

    private void removeCompletedLine(int completedLine) {
        playfieldList.remove(completedLine);
        int widthOfField = playfieldList.get(10).size();
        playfieldList.add(1, makeLine(widthOfField));
    }

    private List<Integer> makeLine(int size) {
        List<Integer> blocks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            blocks.add(0);
        }
        Logger.info(blocks);
        return blocks;
    }

    public boolean isBlinded() {
        return blinded;
    }

    void putLineOnField(int line, List<Integer> completeLine) {
        playfieldList.set(line, completeLine);
    }

    public Score getScoreByName() {
        return score;
    }

    public int getScore() {
        return score.getScore();
    }

    public PointsForAbilities getPointsForAbilities() {
        return points;
    }

    public int getPoints() {
        return points.getPoints();
    }

    public void setBlinded(boolean blinded) {
        this.blinded = blinded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Playfield playfield1 = (Playfield) o;
        return Objects.equals(playfieldList, playfield1.playfieldList)
            && Objects.equals(score, playfield1.score);
    }

    @Override
    public int hashCode() {

        return Objects.hash(playfieldList, score);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
            .append("playfield ")
            .append(NEWLINE);
        int number = 0;
        for (List<Integer> list : playfieldList) {
            stringBuilder.append(number).append(list).append(NEWLINE);
            number++;
        }

        return stringBuilder.toString();
    }

    public int getCounter() {
        return counter;
    }

    public int getPlayfieldSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(int gameSpeed) {
        if (gameSpeed > 10){
            this.gameSpeed = gameSpeed;
        } else {
            this.gameSpeed = 10;
        }
    }
}
