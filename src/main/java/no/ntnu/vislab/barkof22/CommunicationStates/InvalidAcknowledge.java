package no.ntnu.vislab.barkof22.CommunicationStates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import no.ntnu.vislab.barkof22.CommunicationContext;
import no.ntnu.vislab.vislabcontroller.Command;

public class InvalidAcknowledge implements CommunicationState {
    @Override
    public void execute(CommunicationContext context, Command command) {
        Socket host = context.getHost();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(host.getInputStream()));
            while(in.ready()){
                in.read(); //Clear the inputstream, when an invalid response is recieved it could mean that an older command was sent twice, but the response was delayed causing data to be left in the stream.
            }
            context.changeState(new Send());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
