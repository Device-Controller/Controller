/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.vislab.barkoF22;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ThomasSTodal
 */
public class CommunicationProtocolTest
{
    
    public CommunicationProtocolTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of getPower method, of class CommunicationProtocol.
     */
    @Test
    public void testGetPower()
    {
        System.out.println("getPower");
        int pwrState = 0;
        String expResult = ":POWR0CR";
        String result = CommunicationProtocol.getPower(pwrState);
        assertEquals(expResult, result);
        pwrState = 1;
        expResult = ":POWR1CR";
        result = CommunicationProtocol.getPower(pwrState);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPowerState method, of class CommunicationProtocol.
     */
    @Test
    public void testGetPowerState()
    {
        System.out.println("getPowerState");
        String expResult = ":POST?CR";
        String result = CommunicationProtocol.getPowerState();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMute method, of class CommunicationProtocol.
     */
    @Test
    public void testGetMute()
    {
        System.out.println("getMute");
        int muteState = 0;
        String expResult = ":PMUT0CR";
        String result = CommunicationProtocol.getMute(muteState);
        assertEquals(expResult, result);
        muteState = 1;
        expResult = ":PMUT1CR";
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBrightnessGet method, of class CommunicationProtocol.
     */
    @Test
    public void testGetBrightnessGet()
    {
        System.out.println("getBrightnessGet");
        String expResult = "";
        String result = CommunicationProtocol.getBrightnessGet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBrightnessSet method, of class CommunicationProtocol.
     */
    @Test
    public void testGetBrightnessSet()
    {
        System.out.println("getBrightnessSet");
        int brightnessVal = 0;
        String expResult = "";
        String result = CommunicationProtocol.getBrightnessSet(brightnessVal);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContrastGet method, of class CommunicationProtocol.
     */
    @Test
    public void testGetContrastGet()
    {
        System.out.println("getContrastGet");
        String expResult = "";
        String result = CommunicationProtocol.getContrastGet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContrastSet method, of class CommunicationProtocol.
     */
    @Test
    public void testGetContrastSet()
    {
        System.out.println("getContrastSet");
        int contrastVal = 0;
        String expResult = "";
        String result = CommunicationProtocol.getContrastSet(contrastVal);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLampRuntime method, of class CommunicationProtocol.
     */
    @Test
    public void testGetLampRuntime()
    {
        System.out.println("getLampRuntime");
        int lampNo = 0;
        String expResult = "";
        String result = CommunicationProtocol.getLampRuntime(lampNo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLampEstTimeRemaining method, of class CommunicationProtocol.
     */
    @Test
    public void testGetLampEstTimeRemaining()
    {
        System.out.println("getLampEstTimeRemaining");
        int lampNo = 0;
        String expResult = "";
        String result = CommunicationProtocol.getLampEstTimeRemaining(lampNo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLampStatus method, of class CommunicationProtocol.
     */
    @Test
    public void testGetLampStatus()
    {
        System.out.println("getLAMP_STATUS");
        int lampNo = 0;
        String expResult = "";
        String result = CommunicationProtocol.getLampStatus(lampNo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnitTotTime method, of class CommunicationProtocol.
     */
    @Test
    public void testGetUnitTotTime()
    {
        System.out.println("getUNIT_TOT_TIME");
        String expResult = "";
        String result = CommunicationProtocol.getUnitTotTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getThermal method, of class CommunicationProtocol.
     */
    @Test
    public void testGetThermal()
    {
        System.out.println("getTHERMAL");
        String expResult = "";
        String result = CommunicationProtocol.getThermal();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
