package me.fan87.bot.server;

import lombok.Getter;
import lombok.SneakyThrows;
import me.fan87.bot.commands.CommandException;
import me.fan87.bot.config.ConfigManager;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class ServerManager {

    @Getter
    private static Map<String, Server> servers = new HashMap<>();

    @SneakyThrows
    public static Server getServerByName(String name) {
        if (servers.get(name) == null) {
            throw new CommandException("No server found by name you entered!");
        }
        return servers.get(name);
    }

}
