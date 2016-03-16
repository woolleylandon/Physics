package com.example.landon.physics.logic;

public abstract class PhysicsSystem {
    int unknowns;
    boolean solved;
    String formulas;
    String stepSolution;

    public String getStepSolution() {
        return stepSolution;
    }
}