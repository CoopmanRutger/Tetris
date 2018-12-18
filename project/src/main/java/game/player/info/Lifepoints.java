package game.player.info;

import org.pmw.tinylog.Logger;

/**
 * @author Remote Access Tetris aka RAT
 */

public class LifePoints {

    private static final int FIFTEEN = 15;
    private int lifeScore;
    private Thread thread;


    public LifePoints() {
        lifeScore = 10;
    }

    public int getLifePoints() {
        if (lifeScore < FIFTEEN) {
            addAutomaticLifePoints();
        }
        return lifeScore;
    }

    public void addLifePoint() {
        // max 15 levens
        if (lifeScore < FIFTEEN) {
            lifeScore++;
        } else {
            thread.interrupt();
        }
    }

    public boolean removeLifePoint() {
        if (lifeScore > 0) {
            lifeScore--;
            return true;
            // you can play
        }
        return false;
        // you can't play
    }

    // elke x min krijg je een leven bij
    private void addAutomaticLifePoints() {
        thread = new Thread(() -> {
            try {
                while (true) {
                    addLifePoint();
                    //                    new Timer().wait(500);
                    Thread.sleep(500);
                }
            } catch (InterruptedException ignored) {
                Logger.info(ignored.getMessage());
            }
        });
    }

    @Override
    public String toString() {
        return Integer.toString(lifeScore);
    }
}
