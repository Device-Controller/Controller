package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;

/**
 * State for queueing up an idle command, and sending it.
 */
public class QueueIdleCommand implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) {
        context.addCommand(context.getIdleCommand());
        context.changeState(new Idle());
    }
}
