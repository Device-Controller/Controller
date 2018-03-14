package no.ntnu.vislab.barkof22;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import no.ntnu.vislab.barkof22.commands.LampStatus;
import no.ntnu.vislab.barkof22.commands.PowerOn;
import no.ntnu.vislab.barkof22.commands.PowerState;

public class CommunicationDriverTest {
    private CommunicationDriver cd;

    @Before
    public void setUp() throws Exception {
        cd = new CommunicationDriver(new Socket("158.38.101.110", 1025), new PowerState(), new LampStatus(1));
        cd.start();
    }

    @After
    public void tearDown() throws Exception {
    cd.stopThread();
    }

    @Test
    public void run() {
        cd.queueCommand(new PowerOn());
    }

    @Test
    public void queueCommand() {
    }
}