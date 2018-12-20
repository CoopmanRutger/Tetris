package game.player.hero.ability;

import game.player.playfield.Playfield;
import game.player.playfield.PointsForAbilities;
import game.player.playfield.Score;

public class CheeringCrowd implements Ability {

    private int startValue;
    private Boolean available;
    private Playfield playfield;
    private static int numberOfTimesUsed = 0;

    public CheeringCrowd(Playfield playfield) {
        this.playfield = playfield;
        this.startValue = 10;
        this.available = false;
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
        // TODO
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
            System.out.println("activate? nope");
        }
        // TODO hier activeer je hem
    }

    @Override
    public void action() {
        Score score = playfield.getScore();
        score.setDoubleScore(2);
    }

    @Override
    public String toString() {
        return "CheeringCrowd{" +
                ", startValue=" + startValue +
                ", available=" + available +
                '}';
    }
}
