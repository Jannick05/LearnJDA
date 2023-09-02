package dk.jannick.learnjda.events;

import dk.jannick.learnjda.Main;
import dk.jannick.learnjda.managers.TicketManager;
import dk.jannick.learnjda.managers.event.Event;
import dk.jannick.learnjda.utils.MessageUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class ReactionAddedToTicketMessage extends Event {
    public void execute(GenericEvent genericEvent) {
        ButtonInteractionEvent event = (ButtonInteractionEvent) genericEvent;
        if (Objects.requireNonNull(event.getUser()).isBot()) {
            return;
        }
        TicketManager ticketManager = Main.getTicketManager();
        Member member = event.getMember();
        Member client = event.getGuild().getMemberById(ticketManager.getTicket(event.getChannel().getId()));
        Role role = event.getGuild().getRoleById(1147495900374126712L);
        if (Objects.requireNonNull(event.getButton().getId()).equalsIgnoreCase("close")) {
            if (member.getRoles().contains(role)) {
                event.getMessage().getActionRows().clear();
                event.getMessage().editMessageEmbeds(event.getMessage().getEmbeds()).setActionRow(
                        Button.success("open", "Reopen Ticket").withEmoji(Emoji.fromUnicode("✅")),
                        Button.danger("delete", "Delete Ticket").withEmoji(Emoji.fromUnicode("\uD83D\uDDD1\uFE0F"))
                ).queue();
                event.getChannel().asTextChannel().getManager().putMemberPermissionOverride(client.getIdLong(), Collections.singleton(Permission.VIEW_CHANNEL), Collections.singleton(Permission.MESSAGE_SEND)).queue();
                event.replyEmbeds(MessageUtils.createWarningEmbed("The ticket was closed.").build()).queue();
            } else {
                event.replyEmbeds(MessageUtils.createErrorEmbed("You don't have the required permission!").build()).setEphemeral(true).queue();
            }

        } else if (Objects.requireNonNull(event.getButton().getId()).equalsIgnoreCase("open")) {
            if (member.getRoles().contains(role)) {
                event.getMessage().getActionRows().clear();
                event.getMessage().editMessageEmbeds(event.getMessage().getEmbeds()).setActionRow(
                        Button.danger("close", "Close Ticket").withEmoji(Emoji.fromUnicode("\uD83D\uDD10"))
                ).queue();
                Collection<Permission> perms = new ArrayList<>();
                perms.add(Permission.MESSAGE_SEND);
                perms.add(Permission.VIEW_CHANNEL);
                event.getChannel().asTextChannel().getManager().putMemberPermissionOverride(client.getIdLong(), perms, null).queue();
                event.replyEmbeds(MessageUtils.createSuccessEmbed("The ticket was reopened.").build()).queue();
            } else {
                event.replyEmbeds(MessageUtils.createErrorEmbed("You don't have the required permission!").build()).setEphemeral(true).queue();
            }
        } else if (Objects.requireNonNull(event.getButton().getId()).equalsIgnoreCase("delete")) {
            if (member.getRoles().contains(role)) {
                client.getUser().openPrivateChannel()
                        .flatMap(channel -> channel.sendMessage("Your ticket was deleted by **" + member.getUser().getEffectiveName() + "**"))
                        .queue();
                ticketManager.removeTicket(client.getIdLong());
                event.getChannel().delete().queue();
            } else {
                event.replyEmbeds(MessageUtils.createErrorEmbed("You don't have the required permission!").build()).setEphemeral(true).queue();
            }
        }
    }

    public String getEvent() {
        return "ButtonInteractionEvent";
    }
}
