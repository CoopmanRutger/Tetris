package game.events.event;

public class FactoryReset implements Event {

    private String name;
    private Trigger trigger;

    public FactoryReset(String name, Trigger trigger) {
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
}
