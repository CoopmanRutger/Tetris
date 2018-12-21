package game.player.hero.ability;

import game.player.playfield.Playfield;
import game.player.playfield.Score;
import org.pmw.tinylog.Logger;

public class CheeringCrowd implements Ability {

    private static final String NAME = "CheerCrowd";
    private int numberOfTimesUsed;
    private int startValue;
    private Playfield playfield;

    public CheeringCrowd(final Playfield playfield) {
        this.playfield = playfield;
        this.startValue = 6;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getStartValue() {
        return startValue;
    }

    @Override
    public Playfield getPlayfield() {
        return playfield;
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
        Logger.warn("Ability!");
        if (playfield.getPoints() >= startValue) {
            Logger.info("ability activate?");
            playfield.getPointsForAbilities().removePoints(startValue);
            action();
            usedAbility();
            return true;
        }
        return false;
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
