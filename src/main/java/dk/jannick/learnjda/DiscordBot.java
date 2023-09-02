package dk.jannick.learnjda;

import dk.jannick.learnjda.managers.EventHandler;
import dk.jannick.learnjda.managers.SlashCommandHandler;

public class DiscordBot {
    private final Main instance;
    private final SlashCommandHandler commandHandler;
    private final EventHandler eventHandler;

    public DiscordBot(Main instance, SlashCommandHandler commandHandler, EventHandler eventHandler) {
        this.instance = instance;
        this.commandHandler = commandHandler;
        this.eventHandler = eventHandler;
    }
    public Main getInstance() {
        return instance;
    }

    public SlashCommandHandler getCommandHandler() {
        return commandHandler;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }
}
