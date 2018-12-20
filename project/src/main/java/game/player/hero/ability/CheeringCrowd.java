package game.player.hero.ability;

import game.player.playfield.Playfield;
import game.player.playfield.PointsForAbilities;
import game.player.playfield.Score;
import org.pmw.tinylog.Logger;

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
    public void activate(PointsForAbilities points) {
        if (playfield.getPoints() >= startValue){
            points.removePoints(startValue);
            action();
            usedAbility();
        } else {
            Logger.warn("Ability is not available yet!");
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
                '}';
    }
}
