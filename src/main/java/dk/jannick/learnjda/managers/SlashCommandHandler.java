package dk.jannick.learnjda.managers;

import dk.jannick.learnjda.Main;
import dk.jannick.learnjda.commands.TicketCommand;
import dk.jannick.learnjda.managers.slashcommand.ASlashCommand;
import dk.jannick.learnjda.managers.slashcommand.ISlashCommand;
import dk.jannick.learnjda.managers.slashcommand.SlashCommandExecutionInfo;

import java.util.HashMap;
import java.util.Map;

public class SlashCommandHandler {
    private final Map<ASlashCommand, ISlashCommand> slashCommandMap = new HashMap<>();

    public SlashCommandHandler() {
        Main.getJDA().updateCommands().addCommands(
                //new SetupCommand().getSlashCommandData()
                new TicketCommand().getSlashCommandData()
        ).queue();
        try {
            //addSlashCommand(
            //        SetupCommand.class.getDeclaredMethod("execute", SlashCommandExecutionInfo.class).getAnnotation(ASlashCommand.class),
            //        new SetupCommand()
            //);
            addSlashCommand(
                    TicketCommand.class.getDeclaredMethod("execute", SlashCommandExecutionInfo.class).getAnnotation(ASlashCommand.class),
                    new TicketCommand()
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public Map<ASlashCommand, ISlashCommand> getSlashCommandMap() {
        return this.slashCommandMap;
    }

    private void addSlashCommand(ASlashCommand slashCommandAnnotation, ISlashCommand command) {
        this.slashCommandMap.put(slashCommandAnnotation, command);
    }
}
