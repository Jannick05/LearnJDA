package dk.jannick.learnjda.managers.slashcommand;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.List;

public class SlashCommandExecutionInfo {
    private final ISlashCommand command;

    private final SlashCommandInteractionEvent event;

    private final Member sender;

    private final TextChannel textChannel;

    private final ASlashCommand annotation;

    private final List<OptionMapping> options;

    public SlashCommandExecutionInfo(ISlashCommand command, SlashCommandInteractionEvent event, Member sender, TextChannel textChannel, ASlashCommand annotation, List<OptionMapping> options) {
        this.command = command;
        this.event = event;
        this.sender = sender;
        this.textChannel = textChannel;
        this.annotation = annotation;
        this.options = options;
    }

    public ISlashCommand getCommand() {
        return this.command;
    }

    public SlashCommandInteractionEvent getEvent() {
        return this.event;
    }

    public Member getSender() {
        return this.sender;
    }

    public TextChannel getTextChannel() {
        return this.textChannel;
    }

    public ASlashCommand getAnnotation() {
        return this.annotation;
    }

    public List<OptionMapping> getOptions() {
        return this.options;
    }
}
