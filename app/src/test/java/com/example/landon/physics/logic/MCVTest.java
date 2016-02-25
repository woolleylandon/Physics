package com.example.landon.physics.logic;

import com.example.landon.physics.logic.MCV;
import com.example.landon.physics.logic.Measure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Marco Rosas on 2/24/16.
 */
public class MCVTest {

    MCV system;
    @Before
    public void setUp() throws Exception {
        system = new MCV(new Measure(2, "m"), null, null, null);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCountUnknowns() throws Exception {
        assertTrue(system.countUnknowns() == 3);
    }

    @Test
    public void testSolveSystem() throws Exception {
        assertFalse(system.solveSystem());
    }
}