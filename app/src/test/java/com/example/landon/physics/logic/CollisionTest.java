package com.example.landon.physics.logic;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Winson So on 2/24/16.
 */
public class CollisionTest {
    Collision system = new Collision(new Measure(1.375, "m/s"),new Measure(2, "m/s"),new Measure(1, "m/s"), null, new Measure(5, "kg"));

    @Test
    public void testCountUnknowns() throws Exception {
        assertTrue(system.countUnknowns() == 1);
    }

    @Test
    public void testSolveSystem() throws Exception {
        assertTrue(system.solveSystem());

//        assertTrue(system.getMassA().getMagnitude() == 8.0);
//        assertTrue(system.getMassB().getMagnitude() == 2.0);
//        assertTrue(system.getVa().getMagnitude() == 4.0);
//        assertTrue(system.getVb().getMagnitude() == 3.0);
//        assertTrue(system.getVf().getMagnitude() == 3.8);

        System.out.println("A" + system.getMassA().getMagnitude());
        System.out.println("B" + system.getMassB().getMagnitude());
        System.out.println(system.getVa().getMagnitude());
        System.out.println(system.getVb().getMagnitude());
        System.out.println(system.getVf().getMagnitude());
//
//        System.out.println(system.getMassB().isWarning());
//        System.out.println(system.getMassA().isWarning());

    }
}