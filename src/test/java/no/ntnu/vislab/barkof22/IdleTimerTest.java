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
    private IdleTimer timer;

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
        timer = new IdleTimer(1000);
        timer.start();
    }

    @After
    public void tearDown() {
        timer.stopThread();
    }

    /**
     * Test of run method, of class IdleTimer.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        assertEquals(Thread.currentThread().isAlive(), timer.isAlive());
    }
    /**
     * Test of stopThread method, of class IdleTimer.
     */
    @Test
    public void testReadyListener() {
        System.out.println("readyListener");
        boolean result = timer.setOnReadyListener(() -> IdleTimerTest.result = true);
        assertEquals(true, result);
        result = timer.setOnReadyListener(() -> IdleTimerTest.result = true);
        assertEquals(false, result);
    }


    /**
     * Test of stopThread method, of class IdleTimer.
     */
    @Test
    public void testReady() throws InterruptedException {
        System.out.println("ready");
        timer.setOnReadyListener(() -> result = true);
        sleep(2000);
        assertEquals(true, result);
    }


    /**
     * Test of reset method, of class IdleTimer.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        boolean expResult = true;
        boolean result = timer.reset();
        assertEquals(expResult, result);
    }

}
