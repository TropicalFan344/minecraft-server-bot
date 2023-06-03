package me.fan87.bot.commands.impl;

import me.fan87.bot.SimpleEmbedGenerator;
import me.fan87.bot.commands.ServerCommand;
import me.fan87.bot.server.ServerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.Set;

public class CommandServerList extends ServerCommand {
    public CommandServerList() {
        super("server-list", "伺服器列表");
    }

    @Override
    public void onExecute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        Set<String> list = ServerManager.getServers().keySet();
        String output = "";
        int i = 1;
        for (String s : list) {
            output += i + ". " + s;
            i++;
        }
        MessageEmbed message = new EmbedBuilder()
                .setDescription(output)
                .setColor(new Color(0x5DFF51))
                .setTitle("Server List")
                .build();
        event.getHook().editOriginalEmbeds(message).queue();
    }
}
