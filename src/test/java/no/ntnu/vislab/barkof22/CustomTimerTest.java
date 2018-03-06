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
    CustomTimer instance;

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
        instance = new CustomTimerImpl();
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
        CustomTimer.OnReady listener = ()->{};
        boolean expResult = true;
        boolean result = instance.setOnReadyListener(listener);
        assertEquals(expResult, result);
        expResult = false;
        result = instance.setOnReadyListener(listener);
        assertEquals(expResult, result);
    }

    public class CustomTimerImpl extends CustomTimer {

        @Override
        public void run() {}

        public boolean reset() {
            return false;
        }
    }
    
}
