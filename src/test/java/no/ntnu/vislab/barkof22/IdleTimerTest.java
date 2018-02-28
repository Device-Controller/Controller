/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkof22;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kristoffer
 */
public class IdleTimerTest {

    private static boolean result = false;

    public IdleTimerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class IdleTimer.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        IdleTimer timer = new IdleTimer(1000);
        timer.start();
        assertEquals(Thread.currentThread().isAlive(), timer.isAlive());
        timer.stopThread();
    }
    /**
     * Test of stopThread method, of class IdleTimer.
     */
    @Test
    public void testReadyListener() {
        System.out.println("readyListener");
        IdleTimer timer = new IdleTimer(1000);
        timer.start();
        boolean result = timer.setOnReadyListener(() -> this.result = true);
        assertEquals(true, result);
        result = timer.setOnReadyListener(() -> this.result = true);
        assertEquals(false, result);
        timer.stopThread();
    }


    /**
     * Test of stopThread method, of class IdleTimer.
     */
    @Test
    public void testReady() throws InterruptedException {
        IdleTimer timer = new IdleTimer(1000);
        timer.start();
        timer.setOnReadyListener(() -> this.result = true);
        System.out.println("ready");
        sleep(1050);
        assertEquals(true, result);
        timer.stopThread();
    }


    /**
     * Test of reset method, of class IdleTimer.
     */
    @Test
    public void testReset() {
        IdleTimer timer = new IdleTimer(1000);
        timer.start();
        System.out.println("reset");
        boolean expResult = true;
        boolean result = timer.reset();
        assertEquals(expResult, result);
        timer.stopThread();
    }

    /**
     * Test of stopThread method, of class IdleTimer.
     */
    @Test
    public void testStopThread() {
        IdleTimer timer = new IdleTimer(1000);
        timer.start();
        System.out.println("stopThread");
        timer.stopThread();
        try {
            sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(IdleTimerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(false, timer.isAlive());
    }

}
