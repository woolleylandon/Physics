/**
 * Elastic Collision
 *
 * This class represents and Collision system and solves it
 *
 * @author Marco Rosas, Winson So, Landon Woolley
 * @version 0.1
 *
 */

package com.example.landon.physics.logic;

import android.content.Context;

import com.example.landon.physics.R;

public class Collision extends PhysicsSystem implements Solvable {
    /**
     * massA represents the mass from object A
     */
    private Measure massA;
    /**
     * massB represents the mass from object B
     */
    private Measure massB;
    /**
     * va represents the velocity from object A
     */
    private Measure va;
    /**
     * vb represents the velocity from object B
     */
    private Measure vb;
    /**
     * vf represents the velocity from object B+A
     */
    private Measure vf;

    private String collision_formula = "mA*vA1 + mB*vB1 = (mA + mB)*v2";
    private String massA_formula = "mA = (mB*v2 - mB*vB1) / (vA1 - v2)";
    private String massB_formula = "mB = (mA*vA1 - mA*v2) / (v2 - vB1)";
    private String va_formula = "vA1 = (mA + mB)*v2 - mB*vB1) / mA";
    private String vb_formula = "vB1 = (mA + mB)*v2 - mA*vA1) / mB";
    private String vf_formula = "v2 = (mA*vA1 + mB*vB1) / (mA + mB)";
    private Context context;

    /**
     * Constructor, takes Measures as parameters. It accepts nulls.
     *
     * @param vf The final velocity
     * @param vb The velocity from object B
     * @param va The velocity from object A
     * @param massA The mass of object A
     * @param massB The mass of object B
     */
    public Collision(Measure massA, Measure massB, Measure va, Measure vb, Measure vf) {
        this.vf = vf;
        this.vb = vb;
        this.va = va;
        this.massB = massB;
        this.massA = massA;

        solved = false;
        unknowns = countUnknowns();
        stepSolution = "";
    }

    // getter
    public Measure getMassA() {return massA;}
    public Measure getMassB() {return massB;}
    public Measure getVa()    {return va;   }
    public Measure getVb()    {return vb;   }
    public Measure getVf()    {return vf;   }
    public void setContext(Context c) {
        context = c;
    }

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
        String variables = "";

        variables += "\nmassA = ";
        variables += massA == null ? "?" : massA.getMagnitude() + " kg";
        variables += "\nmassB = ";
        variables += massB == null ? "?" : massB.getMagnitude() + " kg";
        variables += "\nva = ";
        variables += va == null ? "?" : va.getMagnitude() + " m/s";
        variables += "\nvb = ";
        variables += vb == null ? "?" : vb.getMagnitude() + " m/s";
        variables += "\nvf = ";
        variables += vf == null ? "?" : vf.getMagnitude() + " m/s";

        stepSolution += variables;
        stepSolution += context.getString(R.string.unknowns);
        stepSolution +=  " " + unknowns;

        if(unknowns == 1) {
            if (massA == null) {
                double finalMassA = (massB.getMagnitude() * vf.getMagnitude() - massB.getMagnitude() * vb.getMagnitude()) / (va.getMagnitude() - vf.getMagnitude());
                massA = new Measure(finalMassA,"kg");
                if(massA.getMagnitude() < 0){
                    massA.setWarning(true);
                }
                //step by step solution
                stepSolution += context.getString(R.string.massA_is);
                stepSolution += "\n" + collision_formula;
                stepSolution += "\n" + massA_formula;
                stepSolution += context.getString(R.string.replace_in_equation);
                stepSolution += String.format("\n mA = (%.1f*%.1f - %.1f*%.1f) / (%.1f - %.1f)", massB.getMagnitude(), vf.getMagnitude(), massB.getMagnitude(), vb.getMagnitude(), va.getMagnitude(), vf.getMagnitude());
                stepSolution += String.format("\n mA = %.1f %s", finalMassA, massA.getUnit());
            }
            else if (massB == null) {
                double finalMassB = (massA.getMagnitude() * va.getMagnitude() - massA.getMagnitude() * vf.getMagnitude()) / (vf.getMagnitude() - vb.getMagnitude());
                massB = new Measure(finalMassB,"kg");
                if(massB.getMagnitude() < 0){
                    massB.setWarning(true);
                }
                //step by step solution
                stepSolution += context.getString(R.string.massB_is);
                stepSolution += "\n" + collision_formula;
                stepSolution += "\n" + massB_formula;
                stepSolution += context.getString(R.string.replace_in_equation);
                stepSolution += String.format("\n mB = (%.1f*%.1f - %.1f*%.1f) / (%.1f - %.1f)", massA.getMagnitude(), va.getMagnitude(), massA.getMagnitude(), vf.getMagnitude(), vf.getMagnitude(), vb.getMagnitude());
                stepSolution += String.format("\n mB = %.1f %s", finalMassB, massB.getUnit());
            }
            else if (va == null) {
                double finalVa = ((massA.getMagnitude() + massB.getMagnitude()) * vf.getMagnitude() - massB.getMagnitude() * vb.getMagnitude())/massA.getMagnitude();
                va = new Measure(finalVa,"m/s");

                //step by step solution
                stepSolution += context.getString(R.string.collision_va_is);
                stepSolution += "\n" + collision_formula;
                stepSolution += "\n" + va_formula;
                stepSolution += context.getString(R.string.replace_in_equation);
                stepSolution += String.format("\n vA1 = (%.1f + %.1f)*%.1f - %.1f*%.1f) / %.1f", massA.getMagnitude(), massB.getMagnitude(), vf.getMagnitude(), massB.getMagnitude(), vb.getMagnitude(), massA.getMagnitude());
                stepSolution += String.format("\n vA1 = %.1f %s", finalVa, va.getUnit());
            }
            else if (vb == null) {
                double finalVb = ((massA.getMagnitude() + massB.getMagnitude()) * vf.getMagnitude() - massA.getMagnitude() * va.getMagnitude())/massB.getMagnitude();
                vb = new Measure(finalVb,"m/s");

                //step by step solution
                stepSolution += context.getString(R.string.collision_vb_is);
                stepSolution += "\n" + collision_formula;
                stepSolution += "\n" + vb_formula;
                stepSolution += context.getString(R.string.replace_in_equation);
                stepSolution += String.format("\n vB1 = (%.1f + %.1f)*%.1f - %.1f*%.1f) / %.1f", massA.getMagnitude(), massB.getMagnitude(), vf.getMagnitude(), massA.getMagnitude(), va.getMagnitude(), massB.getMagnitude());
                stepSolution += String.format("\n vB1 = %.1f %s", finalVb, vb.getUnit());
            }
            else if (vf == null) {
                double finalVf = (massA.getMagnitude() * va.getMagnitude() + massB.getMagnitude() * vb.getMagnitude()) / (massA.getMagnitude() + massB.getMagnitude());
                vf = new Measure(finalVf, "m/s");

                //step by step solution
                stepSolution += context.getString(R.string.collision_vf_is);
                stepSolution += "\n" + collision_formula;
                stepSolution += "\n" + vf_formula;
                stepSolution += context.getString(R.string.replace_in_equation);
                stepSolution += String.format("\n v2 = (%.1f*%.1f + %.1f*%.1f) / (%.1f + %.1f)", massA.getMagnitude(), va.getMagnitude(), massB.getMagnitude(), vb.getMagnitude(), massA.getMagnitude(), massB.getMagnitude());
                stepSolution += String.format("\n v2 = %.1f %s", finalVf, vf.getUnit());
            }
            solved = true;
            return true;
        }
        return false;
    }
}