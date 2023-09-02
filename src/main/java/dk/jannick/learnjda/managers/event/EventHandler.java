package dk.jannick.learnjda.managers.event;

import dk.jannick.learnjda.Main;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class EventHandler implements EventListener {
    public void onEvent(@NotNull GenericEvent genericEvent) {
        try {
            for (Event event : Main.getEventHandler().getEventList()) {
                if (event.getEvent().equals(genericEvent.getClass().getSimpleName()))
                    event.execute(genericEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
