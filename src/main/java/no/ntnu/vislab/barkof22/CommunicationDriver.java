package no.ntnu.vislab.barkof22;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.ntnu.vislab.vislabcontroller.Command;

public class CommunicationDriver extends AbstractThread {

    private final static int IDLE = 0;
    private final static int IDLE_COMMAND = 1;
    private final static int SEND = 2;
    private int state = IDLE;
    private int index = 0;
    private Socket host;
    private ArrayList<Command> idleCommands;
    private ArrayList<Command> outgoingBuffer;
    private Timer timer;
    private final CommunicationContext communicator;

    public CommunicationDriver(Socket host, List<Command> idleCommands) {
        this.host = host;
        this.idleCommands = new ArrayList<>(idleCommands);
        this.outgoingBuffer = new ArrayList<>();
        this.timer = new Timer();
        this.communicator = new CommunicationContext(host);
        this.communicator.setOnAcknowledgeReceivedListener(this::reset);
    }

    private void reset(boolean result) {
        if (result) {
            outgoingBuffer.remove(0);
            state = IDLE;
        }
    }

    public CommunicationDriver(Socket host, Command... commands) {
        this(host, Arrays.asList(commands));
    }

    @Override
    public void run() {
        while (getRunning()) {
            switch (state) {
                case IDLE:
                    if (timer.hasTimerPassed(1000)) {
                        state = IDLE_COMMAND;
                    } else if (outgoingBuffer.size() > 0) {
                        state = SEND;
                    }
                    break;
                case IDLE_COMMAND:
                    outgoingBuffer.add(getIdleCommand());
                    state = SEND;
                    break;
                case SEND:
                    communicator.sendCommand(outgoingBuffer.get(0));
                    timer.reset();
            }
        }
    }

    public void queCommand(Command command) {
        outgoingBuffer.add(command);
    }

    private int getAndIncrementIndex() {
        int old = index;
        index = (index + 1) % idleCommands.size();
        return old;
    }

    private Command getIdleCommand() {
        return (idleCommands.isEmpty()) ? null : idleCommands.get(getAndIncrementIndex());
    }
}
