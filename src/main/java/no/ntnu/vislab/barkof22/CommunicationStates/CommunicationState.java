package no.ntnu.vislab.barkof22.CommunicationStates;

import java.io.IOException;

import no.ntnu.vislab.barkof22.CommunicationContext;

/**
 * The state interface for the state pattern. All classes that are states in this state machine must implement this interface.
 */
public interface CommunicationState {
    void execute(final CommunicationContext context) throws IOException;
}
