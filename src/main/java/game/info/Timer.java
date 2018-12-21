package game.info;

import org.pmw.tinylog.Logger;

import java.util.TimerTask;

public class Timer extends TimerTask {

    private int timerAmount;

    public Timer(int timer) {
        this.timerAmount = timer;
    }

    public int getTimer() {
        return timerAmount;
    }

    @Override
    public void run() {
        timerAmount--;
        Logger.info(timerAmount);
        if (timerAmount <= 0) {
            cancel();
        }
    }

}
