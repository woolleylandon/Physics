package com.example.landon.physics.logic;

public class Collision extends PhysicsSystem implements Solvable {
  Measure massA;
  Measure massB;
  Measure va;
  Measure vb;
  Measure vf;

  public int countUnknowns() {
    return 0;
  }

  public boolean solveSystem() {
    return false;
  }
}
