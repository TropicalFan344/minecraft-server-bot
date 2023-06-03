package me.fan87.bot;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Test2 {

    static String FILE_URL = "https://piston-data.mojang.com/v1/objects/8f3112a1049751cc472ec13e397eade5336ca7ae/server.jar";

    public static void main(String[] args){
        try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("server.jar")) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // handle exception
        }
    }

}
