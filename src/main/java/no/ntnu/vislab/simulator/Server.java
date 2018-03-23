package no.ntnu.vislab.simulator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Server extends Thread {
    private boolean running;
    private ServerSocket socket;
    private Projector projector;
    private ArrayList<Log> logs;

    public interface OnDummy {
        void onDummy(ProjectorCommunicator d);
    }

    private OnDummy onDummyCallback;

    public interface OnChange {
        void onChange();
    }

    private OnChange onChange;

    public Server(Projector projector, OnDummy onDummyCallback, OnChange onChange) {
        this.onDummyCallback = onDummyCallback;
        this.onChange = onChange;
        this.projector = projector;
        this.running = true;
        this.logs = new ArrayList<>();
    }

    public void run() {

        try {
            socket = new ServerSocket(1025);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logs.add(new Log(new Date(), getName(), Log.SYSTEM, "READY"));
        while (running) {
            Socket conn;
            try {
                conn = socket.accept();
                if (conn.getInetAddress().toString().replace("/", "").startsWith("158.38.")) {
                    ProjectorCommunicator d = new ProjectorCommunicator(conn, projector, msg -> {
                        logs.add(msg);
                        onChange.onChange();
                    });
                    onDummyCallback.onDummy(d);
                    d.start();
                } else {
                    logs.add(new Log(new Date(), getName(), Log.CONNECTION, "Connection from" + conn.getInetAddress().toString() + " refused"));
                    conn.close();
                }
            } catch (IOException e) {
                //e.printStackTrace();
                stopRunning();
            }
        }
    }

    public Log readLogLine() {
        try {
            return logs.remove(0);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public void stopRunning() {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
