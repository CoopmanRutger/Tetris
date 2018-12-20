package game.player.hero.ability;

import game.player.playfield.Playfield;
import game.player.playfield.PointsForAbilities;
import game.player.playfield.Score;
import io.netty.util.Timeout;
import org.pmw.tinylog.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class CheeringCrowd implements Ability {

    private int startValue;
    private Playfield playfield;
    private static int numberOfTimesUsed = 0;

    public CheeringCrowd(Playfield playfield) {
        this.playfield = playfield;
        this.startValue = 10;
    }

    @Override
    public int getStartValue() {
        return startValue;
    }

    @Override
    public int getNumberOfTimesUsed() {
        return numberOfTimesUsed;
    }

    @Override
    public void usedAbility() {
        numberOfTimesUsed++;
    }

    @Override
    public boolean activate() {
        if (playfield.getPoints() >= startValue){
            playfield.getPoints().removePoints(startValue);
            action();
            usedAbility();
            return true;
        } else {
            return false;
        }
        Timer timer;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopAction();
            }
        }, 20000);
    }

    @Override
    public void action() {
        Score score = playfield.getScore();
        score.setDoubleScore(2);
    }

    @Override
    public void stopAction() {
        Score score = playfield.getScore();
        score.setDoubleScore(0);
    }

    @Override
    public String toString() {
        return "CheeringCrowd{" +
                ", startValue=" + startValue +
                '}';
    }
}
