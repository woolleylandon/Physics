package com.example.landon.physics.logic;

public class MCA extends PhysicsSystem implements Solvable {
    Measure s0;
    Measure sf;
    Measure v0;
    Measure vf;
    Measure a;
    Measure t;

    public int countUnknowns() {
        return 0;
    }

    public boolean solveSystem() {
        return false;
    }
}