package no.ntnu.vislab.barkof22;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.ntnu.vislab.vislabcontroller.Command;

public class CommunicationDriver extends AbstractThread{

    private Socket host;
    private ArrayList<Command> idleCommands;
    private ArrayList<Command> outgoingBuffer;

    public CommunicationDriver(Socket host, List<Command> idleCommands) {
        this.host = host;
        this.idleCommands = new ArrayList<>(idleCommands);
        this.outgoingBuffer = outgoingBuffer;
    }

    public CommunicationDriver(Socket host, Command... commands){
        this(host, Arrays.asList(commands));
    }

    @Override
    public void run() {

    }
}
