package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;

/**
 * State for when a response did not come in time.
 * This state will either resend the same command,
 * or go into a SendFailed state depending on the number of times it has tried to send this specific command.
 */
public class NoAcknowledge implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) {
        if (context.getSendAttempts() < 3) {
            context.changeState(new Wait());
        } else {
            context.changeState(new SendFailed());
        }
    }
}
