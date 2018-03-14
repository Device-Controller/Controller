package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;

public class NoAcknowledge implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) {
        if(context.getSendAttempts() < 3) {
            context.changeState(new Send());
        } else {
            context.changeState(new SendFailed());
        }
    }
}
