package dk.jannick.learnjda.managers;

import dk.jannick.learnjda.events.ReactionAddedToVerifyMessage;
import dk.jannick.learnjda.managers.event.Event;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    private final List<Event> eventList = new ArrayList<>();

    public EventHandler() {
        this.eventList.add(new ReactionAddedToVerifyMessage());
    }

    public List<Event> getEventList() {
        return this.eventList;
    }
}
