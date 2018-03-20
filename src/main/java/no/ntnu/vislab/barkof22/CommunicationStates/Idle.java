package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.vislabcontroller.Command;

/**
 * Idle state, it checks if there is any new commmands available, if no command is ready for 1000ms it will queue up an idle command and send that instead.
 */
public class Idle implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) {
        Command command = context.getCommand();
        if(command == null && context.hasTimerPassed(1000) && context.hasIdleCommands()){
            context.changeState(new QueueIdleCommand());
        } else if(command != null) {
            context.changeState(new Send());
        }
    }
}
