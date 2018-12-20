package game.player.hero.ability;

import game.player.playfield.Playfield;
import game.player.playfield.PointsForAbilities;
import game.player.playfield.Score;
import io.netty.util.Timeout;
import org.pmw.tinylog.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class CheeringCrowd implements Ability {

    private final String name = "CheerCrowd";
    private int startValue;
    private Playfield playfield;
    private static int numberOfTimesUsed = 0;

    public CheeringCrowd(Playfield playfield) {
        this.playfield = playfield;
        this.startValue = 8;
    }

    @Override
    public String getName() {
        return name;
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
            playfield.getPointsForAbilities().removePoints(startValue);
            action();
            usedAbility();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void action() {
        Score score = playfield.getScoreByName();
        score.setDoubleScore(2);
    }

    @Override
    public void stopAction() {
        Score score = playfield.getScoreByName();
        score.setDoubleScore(0);
    }

    @Override
    public String toString() {
        return "CheeringCrowd{" +
                ", startValue=" + startValue +
                '}';
    }
}
