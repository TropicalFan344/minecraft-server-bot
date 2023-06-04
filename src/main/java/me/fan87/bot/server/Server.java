package me.fan87.bot.server;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Server {

    @Getter
    private Process process;
    @Getter
    private String name;
    @Getter
    private String allocate;
    @Getter
    private String serverUri;
    @Getter
    private String log;

    @SneakyThrows
    public Server(String name, String allocate, String serverUri) {
        this.name = name;
        this.allocate = allocate;
        this.serverUri = serverUri;
        ProcessBuilder processBuilder = new ProcessBuilder()
                .command("java", "-Xms" + allocate, "-jar", "server.jar", "nogui")
                .directory(new File(serverUri));
        new Thread(() -> {
            try {
                Process process = processBuilder.start();
                this.process =process;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.inputCommand("stop");
        }));
        ServerManager.getServers().put(name, this);
    }

    public void inputCommand(String command) {
        OutputStream stream = process.getOutputStream();
        command += "\n";
        try {
            stream.write(command.getBytes(StandardCharsets.UTF_8));
            stream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public String getLog() {
        Scanner scanner = new Scanner(serverUri + "log\\latest.log");
        while (scanner.hasNext()) {
            this.log += scanner.nextLine();
        }
        return this.log;
    }

    public boolean isAlive() {
        return process.isAlive();
    }
}
