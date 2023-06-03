package me.fan87.bot.commands;

import lombok.Getter;
import me.fan87.bot.ServerBot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class ServerCommand {

    public CommandData getCommandData() {
        return new CommandDataImpl(name, description).addOptions(options);
    }


    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final OptionData[] options;

    public ServerBot serverBot;

    public ServerCommand(String name, String description, OptionData... options) {
        this.name = name;
        this.description = description;
        this.options = options;
    }

    public void init() {

    }

    public abstract void onExecute(SlashCommandInteractionEvent event);

}
