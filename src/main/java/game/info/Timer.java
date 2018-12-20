package game.info;

import java.util.TimerTask;

public class Timer extends TimerTask {

    private int timer;

    public Timer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return timer;
    }

    @Override
    public void run() {
        timer--;
        System.out.println(timer);
        if (timer <= 0) {
            cancel();
        }
    }

}
