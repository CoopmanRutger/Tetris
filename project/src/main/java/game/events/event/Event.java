package game.events.event;

public interface Event {

    String getName();
    Trigger getTrigger();
    void activate();

}
