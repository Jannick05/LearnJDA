package dk.jannick.learnjda.commands;

import dk.jannick.learnjda.managers.slashcommand.ASlashCommand;
import dk.jannick.learnjda.managers.slashcommand.ISlashCommand;
import dk.jannick.learnjda.managers.slashcommand.SlashCommandExecutionInfo;
import dk.jannick.learnjda.utils.MessageUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class SetupCommand implements ISlashCommand {

    @Override
    @ASlashCommand(
            name = "setupverify",
            description = "Setup a verify message",
            neededPermission = Permission.ADMINISTRATOR
    )
    public void execute(SlashCommandExecutionInfo info) {
        info.getTextChannel()
                .sendMessageEmbeds(MessageUtils.createSuccessEmbed("> Nedenunder skal du vælge ✅ __Verify__, for at få **adgang** til discorden.").build())
                .setActionRow(
                        Button.success("verifyButton", "Verify")
                                .withEmoji(
                                        Emoji.fromUnicode("✅")
                                )
                )
                .queue();
        //info.getEvent().reply("done!").queue();
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
