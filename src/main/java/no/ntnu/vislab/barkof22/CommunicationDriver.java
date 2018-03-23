package no.ntnu.vislab.barkof22;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.ntnu.vislab.vislabcontroller.Command;

public class CommunicationDriver extends AbstractRunnable {
    private Socket host;
    private ArrayList<Command> idleCommands;
    private ArrayList<Command> outgoingBuffer;
    private final CommunicationContext communicator;

    public interface OnCommandReady{
        void onCommandReady(Command command);
    }
    private OnCommandReady listener;

    public CommunicationDriver(Socket host, List<Command> idleCommands) throws IOException {
        this.host = host;
        this.idleCommands = new ArrayList<>(idleCommands);
        this.outgoingBuffer = new ArrayList<>();
        this.communicator = new CommunicationContext(host.getOutputStream(), host.getInputStream(), outgoingBuffer, idleCommands);
        this.communicator.setOnAcknowledgeReceivedListener(this::handleCommand);
    }

    private void handleCommand(Command command) {
        if(listener != null){
            listener.onCommandReady(command);
        } else {
            System.err.println("Listener was null, command not handled");
        }
    }

    public CommunicationDriver(Socket host, Command... commands) throws IOException {
        this(host, Arrays.asList(commands));
    }

    public void setOnCommandReady(OnCommandReady listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        while (getRunning()) {
            try {
                communicator.execute();
            } catch (IOException e){
                System.out.println(e.getMessage());
                stopThread();

            }
        }
        System.out.println("stopping");
        stopThread();
    }
    public boolean queueCommand(Command command) {
        if(getRunning()) {
            outgoingBuffer.add(command);
        }
        return getRunning();
    }
}
