package com.example.components;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;

import java.net.*;
import java.io.*;

@JSComponent(name="socket")
public class SocketOps {
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    @JSRunnable
    public void connect(String ip, int port) throws IOException {start(ip, port);}

    @JSRunnable
    public void start(String ip, int port) throws IOException {
        client = new Socket(ip, port);
        System.out.println("got connection on port " + port);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(),true);
    }

    @JSRunnable
    public String recv() throws IOException {
        return in.readLine();
    }

    @JSRunnable
    public void send(String msg) throws IOException {
        out.println(msg);
    }

    @JSRunnable
    public String sendWithReturh(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    @JSRunnable
    public void stop() throws IOException {
        in.close();
        out.close();
        client.close();
    }
}
