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
public class CommunicationTimerTest {

    private boolean stopTimerResult;
    private boolean runResult;

    public CommunicationTimerTest() {
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
     * Test of run method, of class CommunicationTimer.
     */
    @Test
    public void testRun() {
        try {
            System.out.println("run");
            CommunicationTimer instance = new CommunicationTimer();
            instance.start();
            try {
                sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(CommunicationTimerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            assertEquals(true, instance.isAlive());
            CustomTimer.OnReady listener = ()-> runResult = true;
            instance.setOnReadyListener(listener);
            assertEquals(false, runResult);
            sleep(2050);
            assertEquals(true, runResult);
            runResult = false;
            instance.reset(true);
            instance.setOnReadyListener(listener);
            assertEquals(false, runResult);
            sleep(30050);
            assertEquals(true, runResult);
            runResult = false;
            instance.reset();
            instance.stopTimer();
            assertEquals(false, runResult);
            sleep(550);
            assertEquals(true, runResult);
            instance.stopThread();
        } catch (InterruptedException ex) {
            Logger.getLogger(CommunicationTimerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of reset method, of class CommunicationTimer.
     */
    @Test
    public void testReset_boolean() {
        System.out.println("reset");
        CommunicationTimer instance = new CommunicationTimer();
        instance.start();
        boolean result = instance.reset(true);
        assertEquals(true, result);
        result = instance.reset(true);
        assertEquals(false, result);
        try {
            sleep(30050);
        } catch (InterruptedException ex) {
            Logger.getLogger(CommunicationTimerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        result = instance.reset(false);
        assertEquals(true, result);
        result = instance.reset(false);
        assertEquals(false, result);
        instance.stopThread();
    }

    /**
     * Test of reset method, of class CommunicationTimer.
     */
    @Test
    public void testReset_0args() {
        System.out.println("reset");
        CommunicationTimer instance = new CommunicationTimer();
        instance.start();
        boolean result = instance.reset();
        assertEquals(true, result);
        result = instance.reset();
        assertEquals(false, result);
        try {
            sleep(2050);
        } catch (InterruptedException ex) {
            Logger.getLogger(CommunicationTimerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        result = instance.reset();
        assertEquals(true, result);
        result = instance.reset();
        assertEquals(false, result);
        instance.stopThread();
    }

    /**
     * Test of stopTimer method, of class CommunicationTimer.
     */
    @Test
    public void testStopTimer() {
        System.out.println("stopTimer");
        CommunicationTimer instance = new CommunicationTimer();
        instance.start();
        instance.setOnReadyListener(()-> stopTimerResult = true);
        instance.stopTimer();
        try {
            sleep(550);
        } catch (InterruptedException ex) {
            Logger.getLogger(CommunicationTimerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(true,stopTimerResult);
        instance.stopThread();
    }

    /**
     * Test of stopThread method, of class CommunicationTimer.
     */
    @Test
    public void testStopThread() {
        System.out.println("stopThread");
        CommunicationTimer instance = new CommunicationTimer();
        
        instance.start();
        boolean expResult = true;
        boolean result = instance.stopThread();
        assertEquals(expResult, result);
    }

}
