package game.events.event;

/**
 * @author Remote Access Tetris aka RAT
 */

public class Event {

    private final String name;
    private final Trigger trigger;

    public Event(final String name, final Trigger trigger) {
        this.name = name;
        this.trigger = trigger;
    }

    public String getName() {
        return name;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void activate() {
        if (getTrigger() == Trigger.SCORE) {
            activateOnScore();
        } else if (getTrigger() == Trigger.TIME) {
            activateOnTime();
        }
    }

    private void activateOnTime() {
        // TODO: event is triggered on certain time
    }

    private void activateOnScore() {
        // TODO: event is triggered on certain score
    }

    @Override
    public String toString() {
        return "Event{"
            + "name='" + name + '\''
            + ", trigger=" + trigger
            + '}';
    }
}
