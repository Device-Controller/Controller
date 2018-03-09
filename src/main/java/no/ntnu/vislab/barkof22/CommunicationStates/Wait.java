package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.vislabcontroller.Command;

import static java.lang.Thread.sleep;

public class Wait implements CommunicationState {
    private long waitTime = 500;
    public Wait(long waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public void execute(final CommunicationContext context, Command command) {
        if(context.hasTimerPassed(waitTime)){
            context.changeState(new ReceiveAcknowledge());
            context.resetTimer();
        } else {
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Command getCommand() {
        return null;
    }
}
