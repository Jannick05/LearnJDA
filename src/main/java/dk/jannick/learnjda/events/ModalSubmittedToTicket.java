package dk.jannick.learnjda.events;

import dk.jannick.learnjda.Main;
import dk.jannick.learnjda.managers.TicketManager;
import dk.jannick.learnjda.managers.event.Event;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.EnumSet;
import java.util.Objects;

public class ModalSubmittedToTicket extends Event {
    private static final Long TICKET_CATEGORY_ID = 960901543182352384L;
    private static final Long STAFF_ROLE_ID = 1147495900374126712L;

    public void execute(GenericEvent genericEvent) {
        ModalInteractionEvent event = (ModalInteractionEvent) genericEvent;
        if (Objects.requireNonNull(event.getUser()).isBot()) {
            return;
        }
        if (event.getModalId().equals("ticket")) {
            TicketManager ticketManager = Main.getTicketManager();
            Member member = event.getMember();
            User user = member.getUser();
            String topic = event.getValue("topic").getAsString();
            String description = event.getValue("description").getAsString();
            Category category = event.getGuild().getCategoryById(TICKET_CATEGORY_ID);
            Long publicRole = event.getGuild().getPublicRole().getIdLong();
            EnumSet<Permission> permission = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND);

            MessageEmbed embed = new EmbedBuilder()
                    .setTitle(user.getName() + "'s Ticket")
                    .setColor(Color.decode("#2f3136"))
                    .setDescription("**Topic** `" + topic + "`")
                    .addField("Description", description, true)
                    .setDescription("**Topic**")
                    .setFooter(user.getName(), user.getEffectiveAvatarUrl())
                    .build();

            event.getGuild().createTextChannel(user.getName() + "-ticket", category)
                    .addRolePermissionOverride(publicRole, null, permission)
                    .addRolePermissionOverride(STAFF_ROLE_ID, permission, null)
                    .addMemberPermissionOverride(member.getIdLong(), permission, null)
                    .flatMap(action -> action.sendMessageEmbeds(embed)
                            .setActionRow(
                                    Button.danger("close", "Close Ticket")
                                            .withEmoji(Emoji.fromUnicode("\uD83D\uDD10"))
                            )
                    )
                    .queue(m -> {
                        m.pin().queue();
                        ticketManager.addTicket(member.getIdLong(), m.getChannel().getId());
                    });
            event.reply("You've created a ticket!").setEphemeral(true).queue();
        }
    }

    public String getEvent() {
        return "ModalInteractionEvent";
    }
}
