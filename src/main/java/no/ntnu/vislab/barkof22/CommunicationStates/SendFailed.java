package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;

public class SendFailed implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) {
        context.getListener().onAcknowledge(context.getAndRemove());
        context.resetSentCounter();
        context.changeState(new Idle());
    }
}
