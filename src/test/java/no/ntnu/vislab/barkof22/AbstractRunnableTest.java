package no.ntnu.vislab.barkof22;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class AbstractRunnableTest {
    private AbstractRunnable thread;

    @Before
    public void setUp() throws Exception {
        thread = new AbstractThreadImpl();
        new Thread(thread).start();
    }

    @After
    public void tearDown() throws Exception {
        thread.stopThread();
    }

    /**
     * Test of run method.
     */
    @Test
    public void testRun() {
        assertEquals(true, thread.getRunning());
    }

    /**
     * Test of stopThread method.
     */
    @Test
    public void testStopThread() {
        System.out.println("stopThread");
        thread.stopThread();
        try {
            sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(AbstractRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(false, thread.getRunning());
    }

    @Test
    public void getRunning() {
        assertEquals(true, thread.getRunning());
        thread.stopThread();
        assertEquals(false, thread.getRunning());
    }

    class AbstractThreadImpl extends AbstractRunnable {

        @Override
        public void run() {
            while(getRunning()){
            }
        }
    }
}