package me.fan87.bot;

import lombok.Getter;
import me.fan87.bot.commands.CommandsManager;
import me.fan87.bot.config.ConfigManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.reflections.Reflections;

public class ServerBot {

    public static ServerBot instance;

    @Getter
    private final JDA jda;
    @Getter private final Reflections reflections;
    @Getter private final CommandsManager commandsManager;
    @Getter private final ConfigManager configManager;

    public ServerBot(){
        instance = this;
        this.reflections = new Reflections(getClass().getPackage().getName());
        this.configManager = new ConfigManager();
        this.jda = JDABuilder
                .createDefault(configManager.getConfig().token)
                .build();


        System.out.println("Preparing Bot...");
        try {
            jda.awaitReady();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("JDA has connected to Discord as user: " + jda.getSelfUser());

        this.commandsManager = new CommandsManager(this);
    }

}
