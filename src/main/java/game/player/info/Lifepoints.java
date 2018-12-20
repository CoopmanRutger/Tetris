package game.player.info;

public class Lifepoints {

    private int lifePoints;
    private Thread thread;

    public Lifepoints() {
        lifePoints = 10;
    }

    public int getLifePoints() {
        if (lifePoints < 15) {
            addAutomaticLifePoints();
        }
        return lifePoints;
    }

    public void addLifePoint() {
        // max 15 levens
        if (lifePoints < 15) {
            lifePoints++;
        } else {
            thread.interrupt();
        }
    }

    public boolean removeLifePoint() {
        if (lifePoints > 0) {
            lifePoints--;
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
                ignored.getMessage();
            }
        });
    }

    @Override
    public String toString() {
        return Integer.toString(lifePoints);
    }
}

