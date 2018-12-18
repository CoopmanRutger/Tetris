package game.player.hero.ability;

import game.player.playfields.playfield.PointsForAbilities;
import org.pmw.tinylog.Logger;

/**
 * @author Remote Access Tetris aka RAT
 */

public class AbilityLvl1 implements Ability {

    private int numberOfTimesUsed;
    private final String name;
    private final int startValue;
    private Boolean available;

    public AbilityLvl1(final String name) {
        this.name = name;
        this.startValue = 500;
        this.available = false;
        numberOfTimesUsed = 0;
    }

    @Override
    public int getStartValue() {
        return startValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Boolean isAvailable() {
        return available;
    }

    @Override
    public int getNumberOfTimesUsed() {
        return numberOfTimesUsed;
    }

    @Override
    public void abilityIsReadyToUse(final PointsForAbilities points) {
        if (points.getPoints() >= startValue) {
            available = true;
        }
        // TODO
    }

    @Override
    public void usedAbility() {
        numberOfTimesUsed++;
    }

    @Override
    public void activate(final PointsForAbilities points) {
        if (available) {
            usedAbility();
            points.removePoints(startValue);
            action();
        } else {
            //System.out.println("activate? nope");
            Logger.info("activate? nope");
        }
        // TODO hier activeer je hem
    }

    @Override
    public void action() {
        // TODO hier voer je hem uit
    }

    @Override
    public String toString() {
        return "AbilityLvl1{"
            + "name='" + name + '\''
            + ", startValue=" + startValue
            + ", available=" + available
            + '}';
    }
}
