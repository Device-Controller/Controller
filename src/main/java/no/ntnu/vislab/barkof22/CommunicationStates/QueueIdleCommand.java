package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;

public class QueueIdleCommand implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) {
        context.addCommand(context.getIdleCommand());
        context.changeState(new Send());
    }
}
