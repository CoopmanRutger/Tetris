package game.events;

import game.events.event.Event;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Remote Access Tetris aka RAT
 */

public class Events {

    private final Set<Event> eventSet;

    public Events() {
        eventSet = new HashSet<>();
    }

    public Set<Event> getEvents() {
        return eventSet;
    }

    public void addEvent(final Event event) {
        eventSet.add(event);
    }

    public void removeEvent(final Event event) {
        eventSet.remove(event);
    }

    @Override
    public String toString() {
        return eventSet.stream()
            .map(Event::toString)
            .collect(Collectors.joining("\n"));
    }
}
