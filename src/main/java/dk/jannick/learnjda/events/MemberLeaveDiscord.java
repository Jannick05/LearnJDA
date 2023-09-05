package dk.jannick.learnjda.events;

import dk.jannick.learnjda.console.ConsoleGUI;
import dk.jannick.learnjda.Main;
import dk.jannick.learnjda.managers.event.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;

import java.awt.*;
import java.util.Objects;

public class MemberLeaveDiscord extends Event {
    public void execute(GenericEvent genericEvent) {
        ConsoleGUI consoleGUI = Main.getConsoleGUI();
        GuildMemberRemoveEvent event = (GuildMemberRemoveEvent) genericEvent;
        if (Objects.requireNonNull(event.getUser()).isBot()) {
            return;
        }
        consoleGUI.log(event.getMember().getUser().getName() + " left the discord server " + event.getGuild().getName(), Color.RED);

    }

    public String getEvent() {
        return "GuildMemberRemoveEvent";
    }
}
