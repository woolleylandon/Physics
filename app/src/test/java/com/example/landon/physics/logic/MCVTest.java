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
    MCV system2;
    @Before
    public void setUp() throws Exception {
        //system = new MCV(new Measure(2, "m"), null, null, null);
         system = new MCV(null,null, new Measure(2, "m/s"), new Measure(1, "s"));
        system2 = new MCV(new Measure(3,"m"),null, new Measure(2, "m/s"), new Measure(1, "s"));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCountUnknowns() throws Exception {
        assertTrue(system2.countUnknowns() == 1);
    }

    @Test
    public void testSolveSystem() throws Exception {
        //assertFalse(system.solveSystem());
        assertTrue(system2.solveSystem());

//        assertTrue(system.getX0().getMagnitude() == 2.0);
//        assertTrue(system.getXf().getMagnitude() == 4.0);
//        assertTrue(system.getV().getMagnitude() == 2.0);
//        assertTrue(system.getT().getMagnitude() == 1.0);

//        System.out.println(system.getX0().getMagnitude());
//        System.out.println(system.getXf().getMagnitude());
//        System.out.println(system.getV().getMagnitude());
//        System.out.println(system.getT().getMagnitude());

        System.out.println(system2.getX0().getMagnitude());
        System.out.println(system2.getXf().getMagnitude());
        System.out.println(system2.getV().getMagnitude());
        System.out.println(system2.getT().getMagnitude());
    }
}