package game.player.hero.ability;

import game.player.playfield.Playfield;

public class Joker implements Ability {

    private static final String NAME = "Joker";
    private int numberOfTimesUsed;
    private int startValue;
    private Playfield playfield;

    public Joker(Playfield playfield) {
        this.startValue = 15;
        this.playfield = playfield;
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
        playfield.setBlinded(true);
    }

    @Override
    public void stopAction() {
        playfield.setBlinded(false);
    }

    @Override
    public Playfield getPlayfield() {
        return playfield;
    }

    @Override
    public String toString() {
        return "Joker{"
            + ", startValue=" + startValue
            + '}';
    }
}
