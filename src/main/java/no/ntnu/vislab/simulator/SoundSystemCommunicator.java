package no.ntnu.vislab.simulator;

import java.net.Socket;

public class SoundSystemCommunicator extends Communicator {
    public SoundSystemCommunicator(Socket host, SoundSystem device, OnLog callback) {
        super(host,callback);
    }
}
