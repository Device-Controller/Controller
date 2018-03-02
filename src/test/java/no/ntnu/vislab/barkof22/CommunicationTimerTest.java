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
 * @author Kristoffer
 */
public class CommunicationTimerTest {
    private CommunicationTimer instance;
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
        instance = new CommunicationTimer();
        instance.start();
        this.runResult = false;
    }

    @After
    public void tearDown() {
        instance.stopThread();
    }

    /**
     * Test of run method, of class CommunicationTimer.
     */
    @Test
    public void testRun() {
        try {
            System.out.println("run");
            try {
                sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(CommunicationTimerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            assertEquals(true, instance.isAlive());
            CustomTimer.OnReady listener = () -> runResult = true;
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
            instance.setOnReadyListener(listener);
            instance.acknowledge();
            assertEquals(false, runResult);
            sleep(550);
            assertEquals(true, runResult);
        } catch (InterruptedException ex) {
            Logger.getLogger(CommunicationTimerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of reset method, of class CommunicationTimer.
     */
    @Test
    public void testReset_boolean() {
        System.out.println("resetWithArgs");
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
    }

    /**
     * Test of reset method, of class CommunicationTimer.
     */
    @Test
    public void testReset_0args() {
        System.out.println("resetNoArgs");
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
    }

    /**
     * Test of acknowledge method, of class CommunicationTimer.
     */
    @Test
    public void testStopTimer() {
        System.out.println("acknowledge");
        instance.setOnReadyListener(() -> runResult = true);
        instance.acknowledge();
        try {
            sleep(550);
        } catch (InterruptedException ex) {
            Logger.getLogger(CommunicationTimerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(true, runResult);
    }
    /**
     * Test of stopThread method, of class CommunicationTimer.
     */
    @Test
    public void testStopThread() {
        System.out.println("stopThread");
        boolean expResult = true;
        boolean result = instance.stopThread();
        assertEquals(expResult, result);
    }

}
