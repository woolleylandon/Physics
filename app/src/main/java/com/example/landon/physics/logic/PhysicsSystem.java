package com.example.landon.physics.logic;

public abstract class PhysicsSystem {
    int unknowns;
    public boolean solved;
    String formulas;
    String stepSolution;

    public String getStepSolution() {
        return stepSolution;
    }
}