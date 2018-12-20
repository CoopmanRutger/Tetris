package game.player.hero.ability;

import game.player.playfield.Playfield;
import game.player.playfield.PointsForAbilities;
import io.netty.util.Timeout;
import org.pmw.tinylog.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class Joker implements Ability{

    private int startValue;
    private Playfield playfield;
    private static int numberOfTimesUsed = 0;

    public Joker(Playfield playfield) {
        this.startValue = 20;
        this.playfield = playfield;
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
        }, 10000);
    }

    @Override
    public void action() {
        playfield.setBlinded(true);
    }

    @Override
    public void stopAction() {
        playfield.setBlinded(false);
    }

    @Override
    public String toString() {
        return "Joker{" +
                ", startValue=" + startValue +
                '}';
    }
}
