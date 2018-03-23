package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;

/**
 * A wait state. The state machine will wait here for the given amount of time before proceeding.
 */
public class Wait implements CommunicationState {

    @Override
    public void execute(final CommunicationContext context) {
        if(context.hasTimerPassed(context.getWaitTime())){
            context.changeState(new Idle());
            context.resetTimer();
        }
    }
}
