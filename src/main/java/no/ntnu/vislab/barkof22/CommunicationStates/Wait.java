package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;

public class Wait implements CommunicationState {
    private long waitTime = 500;
    public Wait(long waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public void execute(final CommunicationContext context) {
        if(context.hasTimerPassed(waitTime)){
            context.changeState(new ReceiveAcknowledge());
        }
    }
}
