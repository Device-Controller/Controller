package no.ntnu.vislab.barkof22.CommunicationStates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.vislabcontroller.Command;

public class ReceiveAcknowledge implements CommunicationState {
    private long timeout = 1500;
    @Override
    public void execute(final CommunicationContext context) {
        try {
            Command command = context.getCommand();
            BufferedReader in = new BufferedReader(new InputStreamReader(context.getHost().getInputStream()));
            if(in.ready()){
                String line = in.readLine();
                System.out.println(line);
                command.setResponse(line);
                if(command.checkAck()){
                    context.changeState(new AcknowledgeReceived());
                } else {
                    context.changeState(new InvalidAcknowledge());
                }
            } else if(context.hasTimerPassed(timeout)){
                context.changeState(new NoAcknowledge());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
