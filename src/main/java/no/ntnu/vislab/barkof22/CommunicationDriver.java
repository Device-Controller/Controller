package no.ntnu.vislab.barkof22;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.ntnu.vislab.vislabcontroller.Command;

public class CommunicationDriver extends AbstractThread {
    private Socket host;
    private ArrayList<Command> idleCommands;
    private ArrayList<Command> outgoingBuffer;
    private final CommunicationContext communicator;

    public CommunicationDriver(Socket host, List<Command> idleCommands) {
        this.host = host;
        this.idleCommands = new ArrayList<>(idleCommands);
        this.outgoingBuffer = new ArrayList<>();
        this.communicator = new CommunicationContext(host, outgoingBuffer, idleCommands);
        this.communicator.setOnAcknowledgeReceivedListener(this::handleCommand);
    }

    private void handleCommand(Command command) {
    }

    public CommunicationDriver(Socket host, Command... commands) {
        this(host, Arrays.asList(commands));
    }

    @Override
    public void run() {
        while (getRunning()) {
            communicator.execute();
        }
    }

    public void queueCommand(Command command) {
        outgoingBuffer.add(command);
    }
}
