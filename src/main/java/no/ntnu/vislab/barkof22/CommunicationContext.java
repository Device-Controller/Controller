package no.ntnu.vislab.barkof22;

import java.net.Socket;

import no.ntnu.vislab.barkof22.CommunicationStates.AcknowledgeReceived;
import no.ntnu.vislab.barkof22.CommunicationStates.CommunicationState;
import no.ntnu.vislab.barkof22.CommunicationStates.Send;
import no.ntnu.vislab.vislabcontroller.Command;

public class CommunicationContext {
    private CommunicationState currentState;
    public interface OnAcknowledge {
        void onAcknowledge(boolean received);
    }

    private OnAcknowledge listener;
    private final Timer timer;
    private int index = 0;
    private int sentCount = 0;
    private int sendAttempts = 0;
    private final Socket host;

    public CommunicationContext(Socket host) {
        this.host = host;
        this.timer = new Timer();
        changeState(new Send());
    }

    public void changeState(final CommunicationState nextState) {
        if (nextState instanceof AcknowledgeReceived) {
            listener.onAcknowledge(true);
        }
        currentState = nextState;
    }
    public void sendCommand(Command command) {
        currentState.execute(this, command);
    }


    public void setOnAcknowledgeReceivedListener(OnAcknowledge listener){
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

    public void incrementSentCounter(){
        sentCount++;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void resetSentCounter(){
        sentCount = 0;
    }
    public void incrementSendAttempts(){
        sendAttempts++;
    }
    public int getSendAttempts(){
        return sendAttempts;
    }

}
