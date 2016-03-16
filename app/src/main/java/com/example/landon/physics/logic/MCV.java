/**
 * Movement with Constant Velocity
 *
 * This class represents and MCV system and solves it
 *
 * @author Marco Rosas, Winson So, Landon Woolley
 * @version 0.1
 *
 */

package com.example.landon.physics.logic;

public class MCV extends PhysicsSystem implements Solvable {
    /**
     * x0 represents the starting position
     */
    private Measure x0;
    /**
     * xf represents the final position
     */
    private Measure xf;
    /**
     * v represents the velocity
     */
    private Measure v;
    /**
     * t represents the time
     */
    private Measure t;

    /**
     * Constructor, takes Measures as parameters. It accepts nulls.
     *
     * @param x0 The starting position
     * @param xf The final position
     * @param v The starting velocity
     * @param t The time
     */
    public MCV(Measure x0, Measure xf, Measure v, Measure t) {
        this.x0 = x0;
        this.xf = xf;
        this.v = v;
        this.t = t;

        solved = false;
        formulas = "sf = s₀ + v*t";
        unknowns = countUnknowns();
        stepSolution = "";
    }

    // getter
    public Measure getX0() {return x0;}
    public Measure getXf() {return xf;}
    public Measure getV()  {return v; }
    public Measure getT()  {return t; }

    /**
     * Method that counts unknown variables
     *
     * @return The number of unknown variables
     */
    public int countUnknowns() {
        int counter = 0;

        if (x0 == null) {counter++;}
        if (xf == null) {counter++;}
        if (v == null)  {counter++;}
        if (t == null)  {counter++;}

        return counter;
    }

    /** 
     * Method that solves the MCV system.
     * Assigns values to those variables that had unknown values.
     * Generates a step by step solution.
     *
     * @return true when the system was solve, false otherwise
     */
    public boolean solveSystem() {

        String variables = "";

        variables += "\ns₀ = ";
        variables += x0 == null ? "?" : x0.getMagnitude() + " " + x0.getUnit();


            if(x0 == null && unknowns == 2 ){
                x0 = new Measure(0,"m");
                x0.setAssumed(true);
                unknowns = 1;
            }
                if(unknowns == 1){
            if(x0 == null){
                    double newX0 = xf.getMagnitude() - (v.getMagnitude() * t.getMagnitude());
                    x0 = new Measure(newX0,"m");
                }

                else if(xf == null){
                    double newXf = x0.getMagnitude() + (v.getMagnitude() * t.getMagnitude());
                    xf = new Measure(newXf,"m");
                }

                else if(v == null){
                    double newV = (xf.getMagnitude() - x0.getMagnitude()) / t.getMagnitude();
                    t = new Measure(newV,"m/s");
                }

                else if(t == null){
                    double newT = (xf.getMagnitude() - x0.getMagnitude()) / v.getMagnitude();
                    t = new Measure(newT,"s");
                    if(t.getMagnitude() < 0){
                        t.setWarning(true);
                    }
                }
                return true;
            }
            return false;
        }
    }