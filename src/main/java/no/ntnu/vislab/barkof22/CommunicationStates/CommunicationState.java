package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;

public interface CommunicationState {
    void execute(final CommunicationContext context);
}
