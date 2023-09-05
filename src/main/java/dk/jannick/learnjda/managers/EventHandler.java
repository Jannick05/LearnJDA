package dk.jannick.learnjda.managers;

import dk.jannick.learnjda.events.*;
import dk.jannick.learnjda.managers.event.Event;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    private final List<Event> eventList = new ArrayList<>();

    public EventHandler() {
        this.eventList.add(new ReactionAddedToVerifyMessage());
        this.eventList.add(new ReactionAddedToTicketMessage());
        this.eventList.add(new ModalSubmittedToTicket());
        this.eventList.add(new MemberJoinDiscord());
        this.eventList.add(new MemberLeaveDiscord());
    }

    public List<Event> getEventList() {
        return this.eventList;
    }
}
