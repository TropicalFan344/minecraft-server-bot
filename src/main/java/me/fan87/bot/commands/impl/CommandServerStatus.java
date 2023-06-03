package me.fan87.bot.commands.impl;

import me.fan87.bot.SimpleEmbedGenerator;
import me.fan87.bot.commands.ServerCommand;
import me.fan87.bot.server.Server;
import me.fan87.bot.server.ServerManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandServerStatus extends ServerCommand {
    public CommandServerStatus() {
        super("server-status", "伺服器狀態", new OptionData(OptionType.STRING, "name", "伺服器名稱", true));
    }

    @Override
    public void onExecute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String name = event.getOption("name").getAsString();
        Server server = ServerManager.getServerByName(name);
        if (server.isAlive()) {
            event.getHook().editOriginalEmbeds(SimpleEmbedGenerator.generateSuccessfulEmbed("伺服氣穩定運行中~~")).queue();
        }else {
            event.getHook().editOriginalEmbeds(SimpleEmbedGenerator.generateWarningEmbed("伺服器沒開喔!")).queue();
        }
    }
}
