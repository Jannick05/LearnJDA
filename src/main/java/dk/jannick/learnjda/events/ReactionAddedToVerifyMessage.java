package dk.jannick.learnjda.events;

import dk.jannick.learnjda.managers.event.Event;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

import java.util.Objects;

public class ReactionAddedToVerifyMessage extends Event {

    private static final String LINK_CHANNEL_ID = "958380341901549588";

    public void execute(GenericEvent genericEvent) {
        ButtonInteractionEvent event = (ButtonInteractionEvent) genericEvent;
        if (Objects.requireNonNull(event.getUser()).isBot()) {
            return;
        }
        if (event.getChannel().getId().equals(LINK_CHANNEL_ID) && Objects.requireNonNull(event.getButton().getId()).equalsIgnoreCase("verifyButton")) {
            Member member = event.getMember();
            Role role = event.getGuild().getRoleById(958380098061467648L);
            AuditableRestAction<Void> action = event
                    .getGuild()
                    .addRoleToMember(member, role);
            action.queue();
            event.reply("Du er blevet verificeret!").setEphemeral(true).queue();
        }
    }

    public String getEvent() {
        return "ButtonInteractionEvent";
    }
}
