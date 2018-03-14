package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;

public class AcknowledgeReceived implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) {
        context.resetSentCounter();
        context.getListener().onAcknowledge(context.getAndRemove());
        context.changeState(new Idle());
    }
}
