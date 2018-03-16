package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.barkof22.commands.Power;
import no.ntnu.vislab.vislabcontroller.Command;

public class Send implements CommunicationState {

    @Override
    public void execute(final CommunicationContext context) {
        Command command = context.getCommand();
        context.getOut().println(command.toString());
        context.incrementSentCounter();
        context.incrementSendAttempts();
        int waitTime = 0;
        if (context.getSentCount() >= 20) {
            waitTime += 5000;
            context.resetSentCounter();
        }
        if (command instanceof Power && ((Power) command).getPowerSetting() == 1) {
            waitTime += 30000;
        } else {
            waitTime += 500;
        }
        context.changeState(new Wait(waitTime));
    }
}
