package me.fan87.bot.commands.impl;

import me.fan87.bot.SimpleEmbedGenerator;
import me.fan87.bot.commands.ServerCommand;
import me.fan87.bot.server.Server;
import me.fan87.bot.server.ServerManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandStop extends ServerCommand {
    public CommandStop() {
        super("stop", "關閉伺服器", new OptionData(OptionType.STRING, "name", "伺服器名稱", true).addChoice());
    }

    @Override
    public void onExecute(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String name = event.getOption("name").getAsString();
        Server server = ServerManager.getServerByName(name);
        server.inputCommand("stop");
        event.getHook().editOriginalEmbeds(SimpleEmbedGenerator.generateSuccessfulEmbed("伺服器關閉成功!")).queue();
    }
}
