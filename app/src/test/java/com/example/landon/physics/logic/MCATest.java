package com.example.landon.physics.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Landon Woolley on 2/24/16.
 */
public class MCATest {

    MCA system;
    MCA system2;

    @Before
    public void setUp() throws Exception {
        system = new MCA(null, null, new Measure(0,"m"), null, new Measure(8,"m/s2"), new Measure(5, "s"));
        system2 = new MCA(new Measure(1000.0, "m"), null, new Measure(-12.0, "m/s"), null, new Measure(-9.8, "m/s2"), new Measure(10.0, "s"));
    }

    @Test
    public void testCountUnknowns() throws Exception {
        assertTrue(system.countUnknowns() == 3);
    }

    @Test
    public void testSolveSystem() throws Exception {
        assertTrue(system.solveSystem());
        assertTrue(system.getS0().getMagnitude() == 0.0);
        assertTrue(system.getSf().getMagnitude() == 100.0);
        assertTrue(system.getV0().getMagnitude() == 0.0);
        assertTrue(system.getVf().getMagnitude() != 0.0);
        assertTrue(system.getA().getMagnitude() == 8.0);
        assertTrue(system.getT().getMagnitude() == 5.0);
    }

    @Test
    public void testSolveSystem1() throws Exception {
        assertTrue(system2.solveSystem());
        assertTrue(system2.getS0().getMagnitude() == 1000.0);
        assertTrue(system2.getSf().getMagnitude() == 390.0);
        assertTrue(system2.getV0().getMagnitude() == -12.0);
        assertTrue(system2.getVf().getMagnitude() != 0.0);
        assertTrue(system2.getA().getMagnitude() == -9.8);
        assertTrue(system2.getT().getMagnitude() == 10.0);

        System.out.println(system2.getS0().getMagnitude() + " ," + system2.getSf().getMagnitude() + " ," +  system2.getV0().getMagnitude() + " ," +  system2.getVf().getMagnitude() + " ," +  system2.getA().getMagnitude() + " ," +  system2.getT().getMagnitude());
    }
}