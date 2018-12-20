package game.player.hero.ability;

import game.player.playfield.Playfield;
import game.player.playfield.Score;

public class CheeringCrowd implements Ability {

    private static int numberOfTimesUsed;
    private final String name = "CheerCrowd";
    private int startValue;
    private Playfield playfield;

    public CheeringCrowd(final Playfield playfield) {
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
        if (playfield.getPoints() >= startValue) {
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
        return "CheeringCrowd{"
            + ", startValue=" + startValue
            + '}';
    }
}
