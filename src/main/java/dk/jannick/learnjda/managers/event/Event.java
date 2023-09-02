package dk.jannick.learnjda.managers.event;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public abstract class Event implements EventListener {
    public void onEvent(@NotNull GenericEvent event) {
        execute(event);
    }

    public abstract void execute(GenericEvent paramGenericEvent);

    public abstract String getEvent();
}
