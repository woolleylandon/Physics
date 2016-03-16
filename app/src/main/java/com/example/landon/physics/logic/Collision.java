package com.example.landon.physics.logic;

public class Collision extends PhysicsSystem implements Solvable {
    Measure massA;
    Measure massB;
    Measure va;
    /**
     * vb represents the velocity from object B
     */
    Measure vb;
    Measure vf;

    /**
     * Constructor, takes Measures as parameters. It accepts nulls.
     *
     * @param vf The final velocity
     * @param vb The velocity from object B
     * @param va The velocity from object A
     * @param massA The mass of object A
     * @param massB The mass of object B
     */
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

    /**
     * Method that counts unknown variables
     *
     * @return The number of unknown variables
     */
    public int countUnknowns() {
        int counter = 0;

        if (massA == null) { counter++; }
        if (massB == null) { counter++; }
        if (va == null)    { counter++; }
        if (vb == null)    { counter++; }
        if (vf == null)    { counter++; }

        return counter;
    }

    /**
     * Method that solves the Collision system.
     * Assigns values to those variables that had unknown values.
     * Generates a step by step solution.
     *
     * @return true when the system was solve, false otherwise
     */
    public boolean solveSystem() {
        if(unknowns == 1) {
            if (massA == null) {
                double finalMassA = (massB.getMagnitude() * vf.getMagnitude() - massB.getMagnitude() * vb.getMagnitude()) / (va.getMagnitude() - vf.getMagnitude());
                massA = new Measure(finalMassA,"kg");
//                if(massA.getMagnitude() < 0){
//                    massA.setWarning(true);
//                }
            }
            else if (massB == null) {
                double finalMassB = (massA.getMagnitude() * va.getMagnitude() - massA.getMagnitude() * vf.getMagnitude()) / (vf.getMagnitude() - vb.getMagnitude());
                massB = new Measure(finalMassB,"kg");
//                if(massB.getMagnitude() < 0){
//                    massB.setWarning(true);
//                }
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