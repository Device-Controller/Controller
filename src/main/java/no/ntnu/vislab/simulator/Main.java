package no.ntnu.vislab.simulator;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;

import static java.lang.Thread.sleep;

public class Main {

    private static ArrayList<ProjectorCommunicator> dummies;
    private static Server server;
    private static boolean running = true;

    public static void main(String[] args) {
        if (args.length == 1) {
            String arg = args[0];
            if (arg.toLowerCase().equals("nogui")) {
                dummies = new ArrayList<>();
                server = new Server(new Projector(), d -> dummies.add(d), () -> {
                });
                server.start();
                Scanner in = new Scanner(System.in);
                String line;
                while (running) {
                    if (in.hasNext()) {
                        dummies.removeIf(d -> !d.isAlive());
                        line = in.next();
                        if (line.equals("quit")) {
                            if (!dummies.isEmpty()) {
                                System.out.println("Stopping all dummies");
                            }
                            for (ProjectorCommunicator projectorCommunicator : dummies) {
                                projectorCommunicator.stopRunning();
                                System.out.println("ProjectorCommunicator " + projectorCommunicator.getName() + "Stopped");
                            }
                            running = false;
                            server.stopRunning();
                            in.close();
                        } else if (line.equals("count")) {
                            System.out.println("SYSTEM: NUMBER OF ACTIVE THREADS = " + dummies.size());
                        }
                    }
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Stopping program");
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Application.launch(App.class, args);
        }
    }
}
