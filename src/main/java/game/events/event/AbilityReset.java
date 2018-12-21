package game.events.event;

import game.player.playfield.Playfield;

public class AbilityReset implements Event {

    private Playfield playfield;
    private Trigger trigger;

    public AbilityReset(Trigger trigger, Playfield playfield) {
        this.playfield = playfield;
        this.trigger = trigger;
    }

    @Override
    public Trigger getTrigger() {
        return trigger;
    }

    @Override
    public void activate() {
        if (getTrigger() == Trigger.SCORE) {
            activateOnScore();
        } else if (getTrigger() == Trigger.TIME) {
            activateOnTime();
        }
    }

    private void activateOnTime() {
        playfield.getPointsForAbilities().removeAllPoints();
    }

    private void activateOnScore() {
        // TODO: event is triggered on certain score
    }
}
