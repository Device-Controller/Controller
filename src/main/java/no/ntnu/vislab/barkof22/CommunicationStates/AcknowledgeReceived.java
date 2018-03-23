package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;

/**
 * State representing an all ok call. The acknowledge received was ok and the command has done its work.
 */
public class AcknowledgeReceived implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) {
        context.getListener().onAcknowledge(context.getAndRemove());
        context.changeState(new Wait());
    }
}
