package no.ntnu.vislab.simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

public class ProjectorCommunicator extends Thread {

    private Socket host;
    private boolean running;
    private PrintWriter pw;
    private BufferedReader br;
    private Projector projector;


    private static int normalWeight = 1;
    private static int noResponseWeight = 0;
    private static int delayedWeight = 0;
    private static int corruptWeight = 0;
    private static int incorrectWeight = 0;

    public interface OnLog {
        void onLog(Log log);
    }

    private OnLog callback;

    public ProjectorCommunicator(Socket host, Projector projector, OnLog callback) {
        setName("D-" + getId());
        this.host = host;
        this.projector = projector;
        this.callback = callback;
        this.running = true;
    }

    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(host.getInputStream()));
            pw = new PrintWriter(host.getOutputStream());
            print(Log.CONNECTION, host.getRemoteSocketAddress() + " CONNECTED");
            while (running) {
                if (isDataAvailable(br)) {
                    String line = br.readLine();
                    print(Log.RECEIVED, line);
                    sleep(50 + new Random().nextInt(100));
                    String response = projector.generateResponse(line);
                    print(Log.GENERATED, response);
                    send(response);
                } else {
                    sleep(10);
                }
            }
            finish();
        } catch (IOException | InterruptedException e) {
            //e.printStackTrace();
            finish();
        }
        print(Log.CONNECTION, "DISCONNECTED");
    }

    public boolean isRunning() {
        return running;
    }

    private void send(String response) throws InterruptedException {
        if (!response.equals("ERROR")) {
            int norm = getNormalWeight();
            int noRe = getNoResponseWeight();
            int corr = getCorruptWeight();
            int delay = getDelayedWeight();
            int incorr = getIncorrectWeight();
            int p = new Random().nextInt(norm + noRe + incorr + corr + delay + 1);
            if (p >= 0 && p <= norm && norm != 0) { //Normal response
                print(Log.SENT_NORMAL, response);
                pw.println(response);
                pw.flush();
            } else if (p >= norm && p <= noRe + norm && noRe != 0) { //No response
                print(Log.SENT_NO_RESPONSE, response);

            } else if (p >= norm + noRe && p <= incorr + noRe + norm && incorr != 0) { //Incorrect Response
                response = projector.generateRandomResponse();
                print(Log.SENT_INCORRECT, response);
                pw.println(response);
                pw.flush();
            } else if (p >= norm + noRe + incorr && p <= corr + incorr + noRe + norm && corr != 0) { //Corrupt Response
                char[] arr = response.toCharArray();
                StringBuilder str = new StringBuilder();
                for (char c : arr) {
                    char ch = (char) (c + (new Random().nextInt(24) - 12));
                    str.append(ch);
                }
                response = str.toString();
                print(Log.SENT_CORRUPTED, response);
                pw.println(response);
                pw.flush();
            } else if (p >= norm + noRe + incorr + corr && p <= delay + corr + incorr + noRe + norm && delay != 0) { //dalayed response
                sleep(2100);
                print(Log.SENT_DELAYED, response);
                pw.println(response);
                pw.flush();
            } else {
                print(Log.SENT, response);
                pw.println(response);
                pw.flush();
            }
        } else {
            print(Log.SENT, response);
            pw.println(response);
            pw.flush();
        }
    }

    private void print(String tag, String line) {
        callback.onLog(new Log(new Date(), getName(), tag, line));
    }

    private void finish() {
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

    private boolean isDataAvailable(BufferedReader br) throws IOException {
        return br.read() == ':';
    }

    public void stopRunning() {
        finish();
    }

    public static int getNormalWeight() {
        return normalWeight;
    }

    public static void setNormalWeight(int normalWeight) {
        ProjectorCommunicator.normalWeight = normalWeight;
    }

    public static int getNoResponseWeight() {
        return noResponseWeight;
    }

    public static void setNoResponseWeight(int noResponseWeight) {
        ProjectorCommunicator.noResponseWeight = noResponseWeight;
    }

    public static int getDelayedWeight() {
        return delayedWeight;
    }

    public static void setDelayedWeight(int delayedWeight) {
        ProjectorCommunicator.delayedWeight = delayedWeight;
    }

    public static int getCorruptWeight() {
        return corruptWeight;
    }

    public static void setCorruptWeight(int corruptWeight) {
        ProjectorCommunicator.corruptWeight = corruptWeight;
    }

    public static int getIncorrectWeight() {
        return incorrectWeight;
    }

    public static void setIncorrectWeight(int incorrectWeight) {
        ProjectorCommunicator.incorrectWeight = incorrectWeight;
    }

    public String getHostIp(){
        return host.getInetAddress().toString().replace("/", "") + ":" + host.getPort();
    }
}
