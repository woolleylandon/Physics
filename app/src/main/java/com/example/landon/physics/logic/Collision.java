package com.example.landon.physics.logic;

public class Collision extends PhysicsSystem implements Solvable {
    Measure massA;
    Measure massB;
    Measure va;
    Measure vb;
    Measure vf;

    public Collision(Measure vf, Measure vb, Measure va, Measure massB, Measure massA) {
        this.vf = vf;
        this.vb = vb;
        this.va = va;
        this.massB = massB;
        this.massA = massA;

        solved = false;
        formulas = "m₁v₁ + m₂v₂ = (m₁ + m₂)v";
        unknowns = countUnknowns();
        //stepSolution = "";
    }

    // getter
    public Measure getMassA() {return massA;}
    public Measure getMassB() {return massB;}
    public Measure getVa()    {return va;   }
    public Measure getVb()    {return vb;   }
    public Measure getVf()    {return vf;   }

    public int countUnknowns() {
        int counter = 0;

        if (massA == null) { counter++; }
        if (massB == null) { counter++; }
        if (va == null)    { counter++; }
        if (vb == null)    { counter++; }
        if (vf == null)    { counter++; }

        return counter;
    }

    public boolean solveSystem() {
        if(unknowns == 1) {
            if (massA == null) {
                double finalMassA = (massB.getMagnitude() * vf.getMagnitude() - massA.getMagnitude() * vb.getMagnitude()) / (va.getMagnitude() - vf.getMagnitude());
                massA = new Measure(finalMassA,"kg");
            }
            else if (massB == null) {
                double finalMassB = (massA.getMagnitude() * va.getMagnitude() - massA.getMagnitude() * vf.getMagnitude()) / (vf.getMagnitude() - vb.getMagnitude());
                massB = new Measure(finalMassB,"kg");
            }
            else if (va == null) {
                double finalVa = ((massA.getMagnitude() + massB.getMagnitude()) * vf.getMagnitude() - massB.getMagnitude() * vb.getMagnitude())/massA.getMagnitude();
                va = new Measure(finalVa,"m/s");
            }
            else if (vb == null) {
                double finalVb = ((massA.getMagnitude() + massB.getMagnitude()) * vf.getMagnitude() - massA.getMagnitude() * va.getMagnitude())/massB.getMagnitude();
                vb = new Measure(finalVb,"m/s");
            }
            else if (vf == null) {
                double finalVf = (massA.getMagnitude() * va.getMagnitude() + massB.getMagnitude() * vb.getMagnitude()) / (massA.getMagnitude() + massB.getMagnitude());
                vf = new Measure(finalVf, "m/s");
            }
            return true;
        }
        return false;
    }
}