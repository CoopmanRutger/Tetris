package game.player.hero.ability;

import game.player.playfield.Playfield;
import game.player.playfield.PointsForAbilities;

public class Joker implements Ability {

    private int startValue;
    private Boolean available;
    private Playfield playfield;
    private static int numberOfTimesUsed = 0;

    public Joker(Playfield playfield) {
        this.startValue = 20;
        this.available = false;
        this.playfield = playfield;
    }

    @Override
    public int getStartValue() {
        return startValue;
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
    public void abilityIsReadyToUse(PointsForAbilities points) {
        if (points.getPoints() >= startValue){
            available = true;
        }
    }

    @Override
    public void usedAbility() {
        numberOfTimesUsed++;
    }

    @Override
    public void activate(PointsForAbilities points) {
        if (available){
            points.removePoints(startValue);
            action();
            usedAbility();
            available = false;
        } else {
            System.out.println("nope can't");
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
                ", available=" + available +
                '}';
    }
}
