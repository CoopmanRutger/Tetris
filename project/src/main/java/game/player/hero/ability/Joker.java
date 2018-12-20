package game.player.hero.ability;

import game.player.playfield.Playfield;
import game.player.playfield.PointsForAbilities;
import org.pmw.tinylog.Logger;

public class Joker implements Ability {

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
    public void activate(PointsForAbilities points) {
        if (playfield.getPoints() >= startValue){
            points.removePoints(startValue);
            action();
            usedAbility();
        } else {
            Logger.warn("Ability is not available!");
        }
        // TODO hier activeer je hem
    }

    @Override
    public void action() {
        playfield.setBlinded(true);
    }

    @Override
    public String toString() {
        return "Joker{" +
                ", startValue=" + startValue +
                '}';
    }
}
