package dk.jannick.learnjda.managers.slashcommand;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ASlashCommand {
    String name();

    Permission neededPermission() default Permission.USE_APPLICATION_COMMANDS;

    String description();
}