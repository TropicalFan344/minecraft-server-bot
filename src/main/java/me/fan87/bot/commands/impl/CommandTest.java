package me.fan87.bot.commands.impl;

import me.fan87.bot.commands.ServerCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;
import java.net.URISyntaxException;

public class CommandTest extends ServerCommand {
    public CommandTest() {
        super("test", "the test");
    }

    @Override
    public void onExecute(SlashCommandInteractionEvent event){
        event.getInteraction().reply("Test Complete").queue();
    }
}
