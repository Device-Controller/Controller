package no.ntnu.vislab.barkof22.CommunicationStates;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.barkof22.commands.Power;
import no.ntnu.vislab.vislabcontroller.Command;

/**
 * State for sending the command currently first in queue.
 * This state is followed by a wait state that varies in length depending on how many commands have been sent and if its the powerON command.
 */
public class Send implements CommunicationState {

    @Override
    public void execute(final CommunicationContext context) {
        Command command = context.getCommand();
        context.getPrintWriter().println(command.toString());
        context.incrementSentCounter();
        context.incrementSendAttempts();
        int waitTime = 0;
        if (context.getSentCount() % 20 == 0) {
            waitTime += 5000;
        }
        if (command instanceof Power && ((Power) command).getPowerSetting() == 1) {
            waitTime += 30000;
        } else {
            waitTime += 500;
        }
        context.setWaitTime(waitTime);
        context.changeState(new ReceiveAcknowledge());
        context.resetTimer();
    }
}
