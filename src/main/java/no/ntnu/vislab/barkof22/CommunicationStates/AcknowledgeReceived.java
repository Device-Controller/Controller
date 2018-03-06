package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.vislabcontroller.Command;

public class AcknowledgeReceived implements CommunicationState {
    @Override
    public void execute(CommunicationContext context, Command command) {
        context.changeState(new Idle());
    }
}
