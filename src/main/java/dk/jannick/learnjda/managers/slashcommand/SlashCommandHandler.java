package dk.jannick.learnjda.managers.slashcommand;

import dk.jannick.learnjda.Main;
import dk.jannick.learnjda.utils.MessageUtils;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashCommandHandler extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getGuild() == null)
            return;
        if (event.getUser().isBot())
            return;
        if (event.getMember() == null)
            return;
        Main.getCommandHandler().getSlashCommandMap().forEach((commandAnnotation, command) -> {
            if (commandAnnotation.name().equalsIgnoreCase(event.getName()))
                if (event.getMember().hasPermission(commandAnnotation.neededPermission())) {
                    command.execute(new SlashCommandExecutionInfo(command, event, event.getMember(), event.getChannel().asTextChannel(), commandAnnotation, event.getOptions()));
                } else {
                    event.replyEmbeds(MessageUtils.createErrorEmbed("You don't have permission to execute this command! You're missing the `" + commandAnnotation.neededPermission().getName() + "` permission.").build(), new net.dv8tion.jda.api.entities.MessageEmbed[0]).queue();
                }
        });
    }
}
