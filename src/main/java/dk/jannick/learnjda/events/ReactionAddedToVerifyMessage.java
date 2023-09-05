package dk.jannick.learnjda.events;

import dk.jannick.learnjda.console.ConsoleGUI;
import dk.jannick.learnjda.Main;
import dk.jannick.learnjda.managers.event.Event;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

import java.awt.*;
import java.util.Objects;

public class ReactionAddedToVerifyMessage extends Event {

    private static final String VERIFY_CHANNEL_ID = Dotenv.configure().load().get("VERIFY_CHANNEL_ID");
    private static final String VERIFY_ROLE_ID = Dotenv.configure().load().get("VERIFY_ROLE_ID");

    public void execute(GenericEvent genericEvent) {
        ConsoleGUI consoleGUI = Main.getConsoleGUI();
        ButtonInteractionEvent event = (ButtonInteractionEvent) genericEvent;
        if (Objects.requireNonNull(event.getUser()).isBot()) {
            return;
        }
        String buttonId = Objects.requireNonNull(event.getButton().getId());
        if (buttonId.equalsIgnoreCase("verifyButton")) {
            if (VERIFY_CHANNEL_ID.equals("null")) {
                event.reply("Contact an Administrator, there's an error!\n`VERIFY_CHANNEL_ID` needed!").setEphemeral(true).queue();
                return;
            }
            if (event.getChannel().getId().equals(VERIFY_CHANNEL_ID) && Objects.requireNonNull(event.getButton().getId()).equalsIgnoreCase("verifyButton")) {
                Member member = event.getMember();
                Role role = event.getGuild().getRoleById(VERIFY_ROLE_ID);
                AuditableRestAction<Void> action = event
                        .getGuild()
                        .addRoleToMember(member, role);
                action.queue();
                event.reply("Du er blevet verificeret!").setEphemeral(true).queue();
                consoleGUI.log(event.getMember().getUser().getName() + " verified", Color.CYAN);
            }
        }
    }

    public String getEvent() {
        return "ButtonInteractionEvent";
    }
}
