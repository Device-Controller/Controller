package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.vislabcontroller.Command;

public interface CommunicationState {
    void execute(final CommunicationContext context, Command command);
    Command getCommand();
}
