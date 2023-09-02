package dk.jannick.learnjda.managers.slashcommand;

import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface ISlashCommand {
    void execute(SlashCommandExecutionInfo paramSlashCommandExecutionInfo);

    SlashCommandData getSlashCommandData();
}
