package dk.jannick.learnjda.commands;

import dk.jannick.learnjda.Main;
import dk.jannick.learnjda.managers.TicketManager;
import dk.jannick.learnjda.managers.slashcommand.ASlashCommand;
import dk.jannick.learnjda.managers.slashcommand.ISlashCommand;
import dk.jannick.learnjda.managers.slashcommand.SlashCommandExecutionInfo;
import dk.jannick.learnjda.utils.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class TicketCommand implements ISlashCommand {
    @Override
    @ASlashCommand(
            name = "ticket",
            description = "Create a ticket"
    )
    public void execute(SlashCommandExecutionInfo info) {
        TicketManager ticketManager = Main.getTicketManager();
        Member member = info.getEvent().getMember();
        Long discordId = member.getIdLong();
        if (ticketManager.hasTicket(discordId)) {
            info.getEvent().reply("You already have an existing ticket!").setEphemeral(true).queue();
        } else {
            TextInput topic = TextInput.create("topic", "What's the ticket about?", TextInputStyle.SHORT)
                    .setPlaceholder("The topic")
                    .setRequired(true)
                    .setMinLength(1)
                    .setMaxLength(10)
                    .build();
            TextInput description = TextInput.create("description", "Describe your topic.", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("The description")
                    .setRequired(true)
                    .setMinLength(10)
                    .setMaxLength(1000)
                    .build();
            Modal modal = Modal.create("ticket", "Ticket")
                    .addActionRows(ActionRow.of(topic), ActionRow.of(description))
                    .build();
            info.getEvent().replyModal(modal).queue();
        }
    }

    @Override
    public SlashCommandData getSlashCommandData() {
        try {
            ASlashCommand commandAnnotation = this.getClass().getDeclaredMethod("execute", SlashCommandExecutionInfo.class).getAnnotation(ASlashCommand.class);
            return Commands.slash(
                    commandAnnotation.name(),
                    commandAnnotation.description()
            );
        } catch (NoSuchMethodException ignored) {
        }
        return null;
    }
}
