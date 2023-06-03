package me.fan87.bot.commands.impl;

import lombok.SneakyThrows;
import me.fan87.bot.SimpleEmbedGenerator;
import me.fan87.bot.commands.CommandException;
import me.fan87.bot.commands.ServerCommand;
import me.fan87.bot.config.ConfigManager;
import me.fan87.bot.server.Server;
import me.fan87.bot.server.ServerManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class CommandLaunch extends ServerCommand {
    public CommandLaunch() {
        super("launch", "啟動伺服器",
                new OptionData(OptionType.STRING, "allocate", "記憶體大小(Recommend 4-8g)", true, false)
                .addChoice("1G", "1G")
                .addChoice("2G", "2G")
                .addChoice("4G", "4G")
                .addChoice("6G", "6G")
                .addChoice("8G", "8G")
                .addChoice("12G", "12G")
                .addChoice("16G", "16G"),
                new OptionData(OptionType.STRING, "name", "伺服器名稱", true));
    }

    @Override
    @SneakyThrows
    public void onExecute(SlashCommandInteractionEvent event){
        event.deferReply().queue();
        ConfigManager config = new ConfigManager();
        String memoryAllocate = event.getOption("allocate").getAsString();
        String name = event.getOption("name").getAsString();
        Server server = new Server(name, memoryAllocate, config.getConfig().serverUri);
        event.getHook().editOriginalEmbeds(SimpleEmbedGenerator.generateSuccessfulEmbed("伺服器成功啟動!名稱: " + name)).queue();
    }
}
