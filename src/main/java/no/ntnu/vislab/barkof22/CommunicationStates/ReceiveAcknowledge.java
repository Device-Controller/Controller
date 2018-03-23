package no.ntnu.vislab.barkof22.CommunicationStates;

import java.io.IOException;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.vislabcontroller.Command;

/**
 * State that waits for upto 2000ms for a response.
 */
public class ReceiveAcknowledge implements CommunicationState {
    private long timeout = 2000;
    @Override
    public void execute(final CommunicationContext context) throws IOException {
            Command command = context.getCommand();
            if(context.getReader().ready()){
                String line = context.getReader().readLine();
                command.setResponse(line);
                if(command.checkAck()){
                    context.changeState(new AcknowledgeReceived());
                } else {
                    context.changeState(new InvalidAcknowledge());
                }
            } else if(context.hasTimerPassed(timeout)){
                context.checkConnection();
                context.changeState(new NoAcknowledge());
            }
    }
}
