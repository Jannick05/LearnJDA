package dk.jannick.learnjda.utils;

import dk.jannick.learnjda.Main;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.Instant;

public class MessageUtils {
    private static final Color BOT_COLOR_SUCCESS = new Color(88, 170, 137);

    private static final Color BOT_COLOR_ERROR = new Color(191, 61, 39);

    private static final Color BOT_COLOR_INFO = new Color(32, 102, 148);

    private static final Color BOT_COLOR_WARNING = new Color(255, 122, 0);

    public static EmbedBuilder createSuccessEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_SUCCESS);
        return embedBuilder;
    }

    public static EmbedBuilder createSuccessEmbed(String message) {
        EmbedBuilder embedBuilder = createSuccessEmbed();
        embedBuilder.setFooter(Main.getJDA().getSelfUser().getName(), Main.getJDA().getSelfUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setDescription(message);
        return embedBuilder;
    }

    public static EmbedBuilder createErrorEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_ERROR);
        return embedBuilder;
    }

    public static EmbedBuilder createErrorEmbed(String message) {
        EmbedBuilder embedBuilder = createErrorEmbed();
        embedBuilder.setFooter(Main.getJDA().getSelfUser().getName(), Main.getJDA().getSelfUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setDescription(message);
        return embedBuilder;
    }

    public static EmbedBuilder createInfoEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_INFO);
        return embedBuilder;
    }

    public static EmbedBuilder createInfoEmbed(String message) {
        EmbedBuilder embedBuilder = createInfoEmbed();
        embedBuilder.setFooter(Main.getJDA().getSelfUser().getName(), Main.getJDA().getSelfUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setDescription(message);
        return embedBuilder;
    }

    public static EmbedBuilder createWarningEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(BOT_COLOR_WARNING);
        return embedBuilder;
    }

    public static EmbedBuilder createWarningEmbed(String message) {
        EmbedBuilder embedBuilder = createWarningEmbed();
        embedBuilder.setFooter(Main.getJDA().getSelfUser().getName(), Main.getJDA().getSelfUser().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setDescription(message);
        return embedBuilder;
    }
}
