package com.example.landon.physics.logic;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Winson So on 2/24/16.
 */
public class CollisionTest {
    Collision system = new Collision();

    @Test
    public void testCountUnknowns() throws Exception {
        assertTrue(system.countUnknowns() == 4);
    }

    @Test
    public void testSolveSystem() throws Exception {
        assertFalse(system.solveSystem() == true);
    }
}