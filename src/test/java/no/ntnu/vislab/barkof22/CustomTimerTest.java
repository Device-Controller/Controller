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
public class CustomTimerTest {
    
    public CustomTimerTest() {
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
     * Test of getTime method, of class CustomTimer.
     */
    @Test
    public void testGetTime() {
        System.out.println("getTime");
        CustomTimer instance = new CustomTimerImpl();
        long expResult = System.currentTimeMillis();
        long result = instance.getTime();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of resetTimer method, of class CustomTimer.
     */
    @Test
    public void testResetTimer() {
        System.out.println("resetTimer");
        CustomTimer instance = new CustomTimerImpl();
        long oldTime = instance.getTime();
        try {
            sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(CustomTimerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance.resetTimer();
        long newTime = System.currentTimeMillis();
        assertEquals(true, oldTime<instance.getTime());
        assertEquals(newTime, instance.getTime());
    }

    /**
     * Test of setOnReadyListener method, of class CustomTimer.
     */
    @Test
    public void testSetOnReadyListener() {
        System.out.println("setOnReadyListener");
        CustomTimer.OnReady listener = ()->System.out.println("listener");;
        CustomTimer instance = new CustomTimerImpl();
        boolean expResult = true;
        boolean result = instance.setOnReadyListener(listener);
        assertEquals(expResult, result);
        expResult = false;
        result = instance.setOnReadyListener(listener);
        assertEquals(expResult, result);
    }

    public class CustomTimerImpl extends CustomTimer {

        public boolean reset() {
            return false;
        }
    }
    
}
