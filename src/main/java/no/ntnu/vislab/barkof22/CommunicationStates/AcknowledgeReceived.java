package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.vislabcontroller.Command;

public class AcknowledgeReceived implements CommunicationState {
    private final Command command;

    public AcknowledgeReceived(Command command) {
        this.command = command;
    }

    @Override
    public void execute(CommunicationContext context, Command command) {
        context.changeState(new Idle());
    }

    @Override
    public Command getCommand() {
        return command;
    }
}
