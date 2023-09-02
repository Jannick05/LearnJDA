package dk.jannick.learnjda;

import dk.jannick.learnjda.managers.EventHandler;
import dk.jannick.learnjda.managers.SlashCommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public final class Main {
    private static final String token = "OTI1NTY1NTA5MzAxMzIxODE4.Gwh6D6.66QjnzrL4Klvr8CEHrEJDVWKJPucyPYxdr6M4w";
    private static Main instance;
    private static SlashCommandHandler commandHandler;
    private static EventHandler eventHandler;

    public static JDA getJDA() {
        return jda;
    }

    private static JDA start() {
        jda = JDABuilder
                .createDefault(token)
                .setAutoReconnect(true)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .setMemberCachePolicy(MemberCachePolicy.ONLINE)
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .enableCache(CacheFlag.ACTIVITY)
                .build();
        return jda;
    }

    public static void main(String[] args) throws InterruptedException {

        commandHandler = new SlashCommandHandler();
        eventHandler = new EventHandler();

        jda.addEventListener(new dk.jannick.learnjda.managers.slashcommand.SlashCommandHandler());
        jda.addEventListener(new dk.jannick.learnjda.managers.event.EventHandler());
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static SlashCommandHandler getCommandHandler() {
        return commandHandler;
    }

    public static EventHandler getEventHandler() {
        return eventHandler;
    }

    private static JDA jda = start();

}