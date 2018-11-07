package game.events;

import game.events.event.Event;
import java.util.HashSet;
import java.util.Set;

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
        StringBuilder res = new StringBuilder();
        for (Event event : events) {
            res.append(event)
                    .append("\n");
        }
        return res.toString();
    }
}