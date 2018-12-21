package tetris.events;

import tetris.events.event.Event;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Events {

    private Set<Event> eventsList;

    public Events() {
        eventsList = new HashSet<>();
    }

    public Set<Event> getEvents() {
        return eventsList;
    }

    public void addEvent(Event event) {
        eventsList.add(event);
    }

    public void removeEvent(Event event) {
        eventsList.remove(event);
    }

    @Override
    public String toString() {
        return eventsList.stream()
                .map(Event::toString)
                .collect(Collectors.joining("\n"));
    }
}
