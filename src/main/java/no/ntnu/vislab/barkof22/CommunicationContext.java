package no.ntnu.vislab.barkof22;

import java.net.Socket;
import java.util.List;

import no.ntnu.vislab.barkof22.CommunicationStates.CommunicationState;
import no.ntnu.vislab.barkof22.CommunicationStates.Idle;
import no.ntnu.vislab.vislabcontroller.Command;

public class CommunicationContext {
    private CommunicationState currentState;
    public interface OnAcknowledge {
        void onAcknowledge(Command received);
    }

    private OnAcknowledge listener;
    private final Timer timer;
    private int index = 0;
    private final List<Command> outgoingBuffer;
    private final List<Command> idleBuffer;
    private int sentCount = 0;
    private int sendAttempts = 0;
    private final Socket host;

    public CommunicationContext(Socket host, List<Command> outgoingBuffer, List<Command> idleBuffer) {
        this.host = host;
        this.outgoingBuffer = outgoingBuffer;
        this.idleBuffer = idleBuffer;
        this.timer = new Timer();
        changeState(new Idle());
    }

    public void changeState(final CommunicationState nextState) {
        resetTimer();
        currentState = nextState;
    }

    public void execute() {
        currentState.execute(this);
    }


    public void setOnAcknowledgeReceivedListener(OnAcknowledge listener) {
        this.listener = listener;
    }

    public boolean hasTimerPassed(long timeout) {
        return timer.hasTimerPassed(timeout);
    }

    public void resetTimer() {
        timer.reset();
    }

    public Socket getHost() {
        return host;
    }

    public void incrementSentCounter() {
        sentCount++;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void resetSentCounter() {
        sentCount = 0;
    }

    public void incrementSendAttempts() {
        sendAttempts++;
    }

    public int getSendAttempts() {
        return sendAttempts;
    }

    public Command getCommand(){
        if(!outgoingBuffer.isEmpty()){
            return outgoingBuffer.get(0);
        }
        return null;
    }
    public boolean hasIdleCommands(){
        return !idleBuffer.isEmpty();
    }

    public Command getIdleCommand() {
        Command idleCommand = idleBuffer.get(index);
        index = (index + 1) % idleBuffer.size();
        return idleCommand;
    }

    public OnAcknowledge getListener() {
        return listener;
    }
    public Command getAndRemove() {
        Command removed = outgoingBuffer.remove(0);
        return removed;
    }
    public boolean addCommand(Command idleCommand) {
        return outgoingBuffer.add(idleCommand);
    }
}
