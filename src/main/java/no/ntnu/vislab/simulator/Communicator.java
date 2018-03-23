package no.ntnu.vislab.simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class Communicator extends Thread {
    protected PrintWriter pw;
    protected BufferedReader br;
    private Socket host;
    private boolean running;

    public interface OnLog {
        void onLog(Log log);
    }

    private OnLog callback;

    public Communicator(Socket host, OnLog callback) {
        this.callback = callback;
        this.host = host;
        this.running = true;
    }

    public boolean isRunning() {
        return running;
    }

    public Socket getHost() {
        return host;
    }

    protected void print(String tag, String line) {
        callback.onLog(new Log(new Date(), getName(), tag, line));
    }

    protected void finish() {
        running = false;
        pw.close();
        try {
            br.close();
        } catch (IOException e) {
            print(Log.ERROR, e.getMessage());
        }
        try {
            host.close();
        } catch (IOException e) {
            print(Log.ERROR, e.getMessage());
        }
    }

    public String getHostIp() {
        return host.getInetAddress().toString().replace("/", "") + ":" + host.getPort();
    }

    public void stopRunning() {
        finish();
    }
}
