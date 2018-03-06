package no.ntnu.vislab.barkof22.CommunicationStates;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.barkof22.commands.PowerOn;
import no.ntnu.vislab.vislabcontroller.Command;

public class Send implements CommunicationState {


    @Override
    public void execute(final CommunicationContext context, Command command) {
        try {
            Socket host = context.getHost();
            PrintWriter out = new PrintWriter(host.getOutputStream());
            out.println(command.getCmd());
            out.flush();
            context.resetTimer();
            context.incrementSentCounter();
            context.incrementSendAttempts();
            if(context.getSentCount()< 20 &! (command instanceof PowerOn)){
                context.changeState(new Wait(500));
            } else if(context.getSentCount() == 20 &! (command instanceof PowerOn)){
                context.changeState(new Wait(5000));
                context.resetSentCounter();
            } else if(context.getSentCount()< 20 && (command instanceof PowerOn)){
                context.changeState(new Wait(30000));
            } else if(context.getSentCount() == 20 && (command instanceof PowerOn)){
                context.changeState(new Wait(35000));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
