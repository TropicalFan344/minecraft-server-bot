package me.fan87.bot;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.*;

public class Test1 {
    public static void main(String[] args) throws IOException {
        OkHttpClient okHttp = new OkHttpClient.Builder().build();
        Response response = okHttp.newCall(new Request.Builder().url("https://piston-data.mojang.com/v1/objects/8f3112a1049751cc472ec13e397eade5336ca7ae/server.jar")
                .get()
                .build()).execute();
        ResponseBody body = response.body();
        InputStream inputStream = body.byteStream();
        File jarFile = new File("server.jar");
        if (!jarFile.exists()) {
            jarFile.createNewFile();
        }
        FileOutputStream jar = new FileOutputStream(jarFile);
        byte[] buffer = new byte[8];
        while(inputStream.read(buffer) > 0) {
            jar.write(buffer);
        }
        jar.close();
    }
}
