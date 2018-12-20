package game.player.hero.ability;

import game.player.playfield.Playfield;
import game.player.playfield.PointsForAbilities;
import io.netty.util.Timeout;
import org.pmw.tinylog.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class Joker implements Ability{

    private final String name = "Joker";
    private int startValue;
    private Playfield playfield;
    private static int numberOfTimesUsed = 0;

    public Joker(Playfield playfield) {
        this.startValue = 15;
        this.playfield = playfield;
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
