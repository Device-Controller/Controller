package no.ntnu.vislab.barkof22.CommunicationStates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.vislabcontroller.Command;

import static java.lang.Thread.sleep;

public class ReceiveAcknowledge implements CommunicationState {
    private long timeout = 2000;
    @Override
    public void execute(final CommunicationContext context, Command command) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(context.getHost().getInputStream()));
            if(in.ready()){
                String line = in.readLine();
                command.setResponse(line);
                if(command.checkAck()){
                    context.changeState(new AcknowledgeReceived(command));
                } else {
                    context.changeState(new InvalidAcknowledge());
                }
            } else if(context.hasTimerPassed(timeout)){
                context.changeState(new Send());
                context.resetTimer();
            } else {
                sleep(5);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Command getCommand() {
        return null;
    }
}
