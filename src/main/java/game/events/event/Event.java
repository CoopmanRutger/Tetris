package game.events.event;

public interface Event {

    Trigger getTrigger();
    void activate();

}
