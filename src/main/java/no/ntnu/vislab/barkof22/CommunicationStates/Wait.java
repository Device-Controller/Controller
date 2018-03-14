package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;

import static java.lang.Thread.sleep;

public class Wait implements CommunicationState {
    private long waitTime = 500;
    public Wait(long waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public void execute(final CommunicationContext context) {
        if(context.hasTimerPassed(waitTime)){
            context.changeState(new ReceiveAcknowledge());
        } else {
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
