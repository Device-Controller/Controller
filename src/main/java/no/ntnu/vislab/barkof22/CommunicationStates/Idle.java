package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.vislabcontroller.Command;

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
