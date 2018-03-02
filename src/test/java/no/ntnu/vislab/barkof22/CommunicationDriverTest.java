package no.ntnu.vislab.barkof22;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import no.ntnu.vislab.vislabcontroller.Command;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class CommunicationDriverTest {
    private CommunicationDriver driver;
    private String testString = "Response";
    private String seperator = "\n%";
    private String sendString;

    @Before
    public void setUp() throws Exception {
        sendString = seperator + testString + seperator + testString + seperator + testString;
        driver = new CommunicationDriver(System.out, new ByteArrayInputStream(sendString.getBytes()));
        driver.start();
    }

    @After
    public void tearDown() throws Exception {
        driver.stopThread();
    }

    @Test
    public void run() {
        TestCommand cmd1 = new TestCommand("1");
        TestCommand cmd2 = new TestCommand("2");
        TestCommand cmd3 = new TestCommand("3");
        assertEquals(true, driver.isAlive());
        try {
            boolean add = driver.addAndSend(cmd1);
            assertEquals(true, add);
            add = driver.addAndSend(cmd2);
            assertEquals(true, add);
            add = driver.addAndSend(cmd3);
            assertEquals(true, add);
            sleep(3000);
            assertEquals(testString, cmd1.getResponse());
            assertEquals(testString, cmd2.getResponse());
            assertEquals(testString, cmd3.getResponse());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addAndSend() {
        assertEquals(0, driver.getCurrentQue());
        TestCommand cmd = new TestCommand("");
        driver.addAndSend(cmd);
        assertEquals(1, driver.getCurrentQue());
    }

    class TestCommand extends Command {

        private static final String TEST = "TEST";

        public TestCommand(String text) {
            super("", "");
            setCmd(getPrefix() + TEST + text + getSuffix());
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