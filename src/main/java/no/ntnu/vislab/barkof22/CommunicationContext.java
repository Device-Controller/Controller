package no.ntnu.vislab.barkof22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import no.ntnu.vislab.barkof22.CommunicationStates.CommunicationState;
import no.ntnu.vislab.barkof22.CommunicationStates.Idle;
import no.ntnu.vislab.vislabcontroller.Command;

import static java.lang.Thread.sleep;

/**
 * The main component in the State Pattern.
 * It holds the input and output streams a copy of the outgoing and idleBuffers.
 * It keeps track of send attempts on the same command, and the amount of commands sent.
 */
public class CommunicationContext {
    private CommunicationState currentState;
    private long waitTime = 500;

    public interface OnAcknowledge {
        void onAcknowledge(Command received);
    }

    private OnAcknowledge listener;
    private final Timer timer;
    private int index = 0;
    private final List<Command> outgoingBuffer;
    private final List<Command> idleBuffer;
    private long sentCount = 0;
    private int sendAttempts = 0;
    private BufferedReader reader;
    private PrintWriter printWriter;
    private OutputStream outputStream;
    private InputStream inputStream;

    public CommunicationContext(OutputStream outputStream, InputStream inputStream, List<Command> outgoingBuffer, List<Command> idleBuffer) {
        this.outgoingBuffer = outgoingBuffer;
        this.idleBuffer = idleBuffer;
        this.timer = new Timer();
        changeState(new Idle());
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.printWriter = new PrintWriter(outputStream, true);

    }

    public void changeState(final CommunicationState nextState) {
        System.out.println(nextState);
        currentState = nextState;
    }

    public void setWaitTime(long time) {
        waitTime = time;
    }

    public long getWaitTime() {
        return waitTime;
    }

    /**
     * Executes the current state of the state machine.
     */
    public void execute() throws IOException {
        currentState.execute(this);
    }


    /**
     * Sets the listener that should receive the command that was sent when it was successful or failed.
     *
     * @param listener listener that should receive the command that was sent when it was successful or failed.
     */
    public void setOnAcknowledgeReceivedListener(OnAcknowledge listener) {
        this.listener = listener;
    }

    /**
     * Checks if the timer has passed the timeout. it sleeps for 5ms for performance reasons.
     *
     * @param timeout the amount of time that has to have passed since last reset.
     * @return true if the current time has passed the timeout.
     */
    public boolean hasTimerPassed(long timeout) {
        try {
            sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return timer.hasTimerPassed(timeout);
    }

    /**
     * Resets the timer.
     */
    public void resetTimer() {
        timer.reset();
    }

    /**
     * Returns the PrintWriter that is the output.
     *
     * @return the PrintWriter that is the output.
     */
    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    /**
     * Returns the BufferedReader that is the input.
     *
     * @return the PrintWriter that is the input.
     */
    public BufferedReader getReader() {
        return reader;
    }

    /**
     * Increments the total amount of commands sent by 1.
     */
    public void incrementSentCounter() {
        sentCount++;
    }

    /**
     * Returns the total amount of commands sent.
     *
     * @return the total amount of commands sent.
     */
    public long getSentCount() {
        return sentCount;
    }

    /**
     * Increments the counter for send attempts for the currently queued command.
     */
    public void incrementSendAttempts() {
        sendAttempts++;
    }


    /**
     * Returns the amount of send attempts.
     *
     * @return the amount of send attempts.
     */
    public int getSendAttempts() {
        return sendAttempts;
    }

    /**
     * Resets the amount of send attempts back to 0.
     */
    public void resetSendAttempts() {
        sendAttempts = 0;
    }

    /**
     * Returns the command currently in queue, or null if no commands are queued.
     *
     * @return the command currently in queue, or null if no commands are queued.
     */
    public Command getCommand() {
        if (!outgoingBuffer.isEmpty()) {
            return outgoingBuffer.get(0);
        }
        return null;
    }

    /**
     * Returns true if there are commands in the idleCommands list.
     *
     * @return true if there are commands in the idleCommands list.
     */
    public boolean hasIdleCommands() {
        return !idleBuffer.isEmpty();
    }

    /**
     * Returns an idle command from the idle commands list.
     *
     * @return an idle command from the idle commands list.
     */
    public Command getIdleCommand() {
        Command idleCommand = idleBuffer.get(index);
        index = (index + 1) % idleBuffer.size();
        return idleCommand;
    }

    /**
     * Returns the current listener for OnAcknowledge.
     *
     * @return the current listener for OnAcknowledge.
     */
    public OnAcknowledge getListener() {
        return listener;
    }

    /**
     * Returns the currently queued command and removes it from the list.
     *
     * @return the currently queued command and removes it from the list.
     */
    public Command getAndRemove() {
        return outgoingBuffer.remove(0);
    }

    /**
     * Adds a command to the outgoing buffer.
     *
     * @param command the command to add to the outgoing buffer.
     * @return true if adding was successful.
     */
    public boolean addCommand(Command command) {
        return outgoingBuffer.add(command);
    }

    public void checkConnection() throws IOException {
        outputStream.write(new byte[1], 0, 1);
    }

}
