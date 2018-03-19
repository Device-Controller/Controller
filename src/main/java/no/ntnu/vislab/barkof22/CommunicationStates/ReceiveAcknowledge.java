package no.ntnu.vislab.barkof22.CommunicationStates;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.vislabcontroller.Command;

public class ReceiveAcknowledge implements CommunicationState {
    private long timeout = 1500;
    @Override
    public void execute(final CommunicationContext context) {
        try {
            Command command = context.getCommand();
            if(context.getIn().ready()){
                String line = context.getIn().readLine();
                command.setResponse(line);
                if(command.checkAck()){
                    context.changeState(new AcknowledgeReceived());
                } else {
                    context.changeState(new InvalidAcknowledge());
                }
            } else if(context.hasTimerPassed(timeout)){
                context.changeState(new NoAcknowledge());
            }
        } catch (IOException e) {
            Logger.getLogger(ReceiveAcknowledge.class.getName()).log(Level.SEVERE,  e.getMessage(), e);
        }
    }
}
