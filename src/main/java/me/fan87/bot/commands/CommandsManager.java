package me.fan87.bot.commands;

import lombok.SneakyThrows;
import me.fan87.bot.ServerBot;
import me.fan87.bot.SimpleEmbedGenerator;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandsManager {

    private final ExecutorService threadPool = Executors.newFixedThreadPool(69);
    private final List<ServerCommand> commands = new ArrayList<>();


    @SneakyThrows
    public CommandsManager(ServerBot serverBot) {

        for (Class<? extends ServerCommand> commandClazz : serverBot.getReflections().getSubTypesOf(ServerCommand.class)) {
            try {
                ServerCommand command = commandClazz.getConstructor().newInstance();
                command.serverBot = serverBot;
                commands.add(command);
                command.init();
            } catch (NoSuchMethodException ignored) {
//                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<String> guilds = new ArrayList<>();
        for (Guild guild : serverBot.getJda().getGuilds()) {
            guilds.add(guild.getId());
        }
        for (String guild : guilds) {
            if (serverBot.getJda().getGuildById(guild) == null) {
                continue;
            }
            CommandListUpdateAction action = serverBot.getJda().getGuildById(guild).updateCommands();
            for (ServerCommand command : commands) {
                action.addCommands(command.getCommandData());
            }
            action.queue();
        }


        // Register Listeners
        serverBot.getJda().addEventListener(new ListenerAdapter() {
            @Override
            public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
                if (!event.getChannel().getName().equals("minecraft伺服器")) {
                    event.reply("請在專用頻道" + event.getGuild().getTextChannelsByName("minecraft伺服器", true).get(0).getAsMention() + "使用指令").queue();
                    return;
                }
                for (ServerCommand command : commands) {
                    if (command.getName().equals(event.getName())) {
                        threadPool.submit(() -> {
                            try {
                                System.out.println("[CommandsManager] [" + Thread.currentThread().getName() + "] Executing Command: " + event.getName());
                                command.onExecute(event);
                                System.out.println("[CommandsManager] [" + Thread.currentThread().getName() + "] Command Executed");
                            } catch (Throwable throwable) {
                                if (throwable instanceof CommandException) {
                                    try {

                                        event.getHook().editOriginalEmbeds(
                                                SimpleEmbedGenerator.generateErrorEmbed(throwable.getMessage())
                                        ).queue();

                                        event.getInteraction().replyEmbeds(
                                                SimpleEmbedGenerator.generateErrorEmbed(throwable.getMessage())
                                        ).queue();
                                    } catch (Throwable e) {

                                    }
                                } else {
                                    throwable.printStackTrace();
                                    event.getHook().editOriginalEmbeds(
                                            // TODO: Error report system
                                            SimpleEmbedGenerator.generateErrorEmbed("Something went wrong while handling the command! Error: " + throwable.getMessage())
                                    ).queue();

                                    event.getHook().editOriginalEmbeds(
                                            // TODO: Error report system
                                            SimpleEmbedGenerator.generateErrorEmbed("Something went wrong while handling the command! Error: " + throwable.getMessage())
                                    ).queue();
                                }
                            }
                        });
                        return;
                    }
                }
            }
        });
        serverBot.getJda().addEventListener(new ListenerAdapter() {
            @Override
            public void onGuildJoin(@NotNull GuildJoinEvent event) {
                String guild = event.getGuild().getId();
                CommandListUpdateAction action = serverBot.getJda().getGuildById(guild).updateCommands();
                for (ServerCommand command : commands) {
                    action.addCommands(command.getCommandData());
                }
                action.queue();
                event.getGuild().createTextChannel("minecraft伺服器").queue();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                event.getGuild().getTextChannelsByName("minecraft伺服器", true).get(0).sendMessage("請在此頻道執行指令!").queue();
            }
        });
    }



}
