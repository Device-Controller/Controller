package no.ntnu.vislab.barkof22;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.net.Socket;

import no.ntnu.vislab.vislabcontroller.Command;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

public class IdleTest {
    private CommunicationDriver driver;
    private String host = "158.38.101.143";
    private int port = 1025;
    private Command idleCommand1;
    private Command idleCommand2;

    @Before
    public void setUp() throws Exception {
        idleCommand1 = new TestCommand("LST1?");
        idleCommand2 = new TestCommand("POWR1");
        driver = new CommunicationDriver(new Socket(host, port), idleCommand1, idleCommand2);
        driver.start();
    }

    @After
    public void tearDown() throws Exception {
        driver.stopThread();
    }

    @Test
    public void testRun() {
        try {
            sleep(2600);
            assertEquals(true ,idleCommand1.getResponse() != null);
            assertEquals(true ,idleCommand2.getResponse() != null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class TestCommand extends Command {

        public TestCommand(String text) {
            super(":", "");
            setCmd(getPrefix() + text + getSuffix());
        }

        @Override
        public String getCmd() {
            return null;
        }

        @Override
        public void setResponse(String response) {
            super.setResponse(response);
        }

        @Override
        public boolean checkAck() {
            return false;
        }
    }

}