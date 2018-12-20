package game.events;

import game.events.event.Event;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Events {

    private Set<Event> events;

    public Events() {
        events = new HashSet<>();
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    @Override
    public String toString() {
        return events.stream()
                .map(Event::toString)
                .collect(Collectors.joining("\n"));
    }
}