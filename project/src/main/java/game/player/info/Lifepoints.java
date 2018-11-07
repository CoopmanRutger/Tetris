package game.player.info;

public class Lifepoints {

    private int lifePoints;
    private Thread thread;

    public Lifepoints() {
        lifePoints = 10;
    }

    public int getLifePoints() {
        if (lifePoints < 15){
            addAutomaticLifePoints();
        }
        return lifePoints;
    }

    public void addLifePoint(){
        if (lifePoints < 15){ // max 15 levens
            lifePoints ++;
        } else {
            thread.interrupt();
        }
    }

    public boolean removeLifePoint(){
        if (lifePoints > 0){
            lifePoints --;
            return true; // you can play
        }
        return false; // you can't play
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
        return  Integer.toString(lifePoints);
    }
}

