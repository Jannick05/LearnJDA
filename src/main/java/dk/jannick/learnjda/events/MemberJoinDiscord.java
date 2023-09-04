package dk.jannick.learnjda.events;

import dk.jannick.learnjda.ConsoleGUI;
import dk.jannick.learnjda.Main;
import dk.jannick.learnjda.managers.event.Event;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import java.awt.*;
import java.util.Objects;

public class MemberJoinDiscord extends Event {
    private static final String WELCOME_CHANNEL_ID = Dotenv.configure().load().get("WELCOME_CHANNEL_ID");
    private static final String WELCOME_ROLE_ID = Dotenv.configure().load().get("WELCOME_ROLE_ID");

    public void execute(GenericEvent genericEvent) {
        ConsoleGUI consoleGUI = Main.getConsoleGUI();
        GuildMemberJoinEvent event = (GuildMemberJoinEvent) genericEvent;
        if (Objects.requireNonNull(event.getUser()).isBot()) {
            return;
        }
        Guild guild = event.getGuild();
        if(!WELCOME_CHANNEL_ID.equals("null")) {
            TextChannel channel = guild.getTextChannelById(WELCOME_CHANNEL_ID);
            channel.sendMessage("Velkommen til " + event.getMember().getAsMention()).queue();
        }
        if(!WELCOME_ROLE_ID.equals("null")) {
            Role role = event.getGuild().getRoleById(WELCOME_ROLE_ID);
            guild.addRoleToMember(event.getMember(), role).queue();
        }
        consoleGUI.log(event.getMember().getUser().getName() + " joined the discord server " + event.getGuild().getName(), Color.GREEN);

    }

    public String getEvent() {
        return "GuildMemberJoinEvent";
    }
}

