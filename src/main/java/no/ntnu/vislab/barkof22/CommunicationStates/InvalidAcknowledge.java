package no.ntnu.vislab.barkof22.CommunicationStates;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import no.ntnu.vislab.barkof22.CommunicationContext;

/**
 * State for when the acknowledge was incorrect.
 * This state will clear the incoming buffer before either trying to resend the same command again, or go into a SendFailed state.
 */
public class InvalidAcknowledge implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) {
        try {
            while (context.getReader().ready()) {
                context.getReader().read(); //Clear the inputstream, when an invalid response is recieved it could mean that an older command was sent twice, but the response was delayed causing data to be left in the stream.

            }
            if (context.getSendAttempts() < 3) {
                context.changeState(new Wait());
            } else {
                context.changeState(new SendFailed());
            }
        } catch (IOException e) {
            Logger.getLogger(InvalidAcknowledge.class.getName()).log(Level.SEVERE,  e.getMessage(), e);
        }
    }
}