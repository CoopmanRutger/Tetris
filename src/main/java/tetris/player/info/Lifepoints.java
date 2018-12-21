package tetris.player.info;

import org.pmw.tinylog.Logger;

public class Lifepoints {

    private static final int FIFTEEN = 15;
    private int lifepointsAmount;
    private Thread thread;

    public Lifepoints() {
        lifepointsAmount = 10;
    }

    public int getLifePoints() {
        if (lifepointsAmount < FIFTEEN) {
            addAutomaticLifePoints();
        }
        return lifepointsAmount;
    }

    public void addLifePoint() {
        // max 15 levens
        if (lifepointsAmount < FIFTEEN) {
            lifepointsAmount++;
        } else {
            thread.interrupt();
            throw new IllegalStateException("Can't be more than 15");
        }
    }

    public boolean removeLifePoint() {
        if (lifepointsAmount > 0) {
            lifepointsAmount--;
            // you can play
            return true;
        }
        // you can't play
        return false;
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
        return Integer.toString(lifepointsAmount);
    }
}

