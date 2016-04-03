/**
 * Movement with Constant Velocity
 *
 * This class represents and MCV system and solves it
 *
 * @author Marco Rosas, Winson So, Landon Woolley
 * @version 0.1
 */

package com.example.landon.physics.logic;
import android.content.Context;
import com.example.landon.physics.R;

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

    String xf_formula = " xf = x0 + v(t)";
    String x0_formula = " x0 = xf - v(t)";
    String v_formula = " v = (xf - x0) / t";
    String t_formula = " t = (xf - x0) / v";

    private Context context;

    /**
     * Constructor, takes Measures as parameters. It accepts nulls.
     *
     * @param x0 The starting position
     * @param xf The final position
     * @param v  The starting velocity
     * @param t  The time
     */
    public MCV(Measure x0, Measure xf, Measure v, Measure t) {
        this.x0 = x0;
        this.xf = xf;
        this.v = v;
        this.t = t;

        solved = false;
        formulas = "sf = s0 + v * t";
        unknowns = countUnknowns();
        stepSolution = "";
    }

    // getter
    public Measure getX0() {
        return x0;
    }

    public Measure getXf() {
        return xf;
    }

    public Measure getV() {
        return v;
    }

    public Measure getT() {
        return t;
    }

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
        if (x0 == null) {
            counter++;
        }
        if (xf == null) {
            counter++;
        }
        if (v == null) {
            counter++;
        }
        if (t == null) {
            counter++;
        }
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

        stepSolution += context.getString(R.string.first_unknowns);

        String variables = "";
        variables += "\nx0 = ";
        variables += x0 == null ? "?" : x0.getMagnitude() + " " + x0.getUnit();
        variables += "\nxf = ";
        variables += xf == null ? "?" : xf.getMagnitude() + " " + xf.getUnit();
        variables += "\nv = ";
        variables += v == null ? "?" : v.getMagnitude() + " " + v.getUnit();
        variables += "\nt = ";
        variables += t == null ? "?" : t.getMagnitude() + " " + t.getUnit();

        stepSolution += variables;
        stepSolution += context.getString(R.string.unknowns);
        stepSolution += " " + unknowns;

        if (x0 == null && unknowns == 2) {
            x0 = new Measure(0, "m");
            x0.setAssumed(true);
            unknowns = countUnknowns();
            stepSolution += context.getString(R.string.starting_position_is0);
            stepSolution += context.getString(R.string.unknowns);
            stepSolution += " " + unknowns;
        }

        if (unknowns == 1) {
            if (x0 == null) {
                double initialPositionValue = xf.getMagnitude() - (v.getMagnitude() * t.getMagnitude());
                x0 = new Measure(initialPositionValue, "m");

                stepSolution += context.getString(R.string.x0_is);
                stepSolution += xf_formula;
                stepSolution += "\n" + x0_formula;
                stepSolution += context.getString(R.string.replace_in_equation);
                stepSolution += String.format("\n x0 = %.3f - %.3f(%.3f)", xf.getMagnitude(), v.getMagnitude(), t.getMagnitude());
                stepSolution += String.format("\n x0 = %.3f %s", initialPositionValue, x0.getUnit());
            } else if (xf == null) {
                double finalPositionValue = x0.getMagnitude() + (v.getMagnitude() * t.getMagnitude());
                xf = new Measure(finalPositionValue, "m");

                stepSolution += context.getString(R.string.xf_is);
                stepSolution += xf_formula;
                stepSolution += context.getString(R.string.replace_in_equation);
                stepSolution += String.format("\n xf = %.3f + %.3f(%.3f)", x0.getMagnitude(), v.getMagnitude(), t.getMagnitude());
                stepSolution += String.format("\n xf = %.3f %s", finalPositionValue, xf.getUnit());
            } else if (v == null) {
                double velocityValue = (xf.getMagnitude() - x0.getMagnitude()) / t.getMagnitude();
                v = new Measure(velocityValue, "m/s");

                stepSolution += context.getString(R.string.v_is);
                stepSolution += xf_formula;
                stepSolution += "\n" + v_formula;
                stepSolution += context.getString(R.string.replace_in_equation);
                stepSolution += String.format("\n v = (%.3f - %.3f) / %.3f", xf.getMagnitude(), x0.getMagnitude(), t.getMagnitude());
                stepSolution += String.format("\n v = %.3f %s", velocityValue, v.getUnit());
            } else if (t == null) {
                double timeValue = (xf.getMagnitude() - x0.getMagnitude()) / v.getMagnitude();
                t = new Measure(timeValue, "s");

                stepSolution += context.getString(R.string.t_is);
                stepSolution += xf_formula;
                stepSolution += "\n" + t_formula;
                stepSolution += context.getString(R.string.replace_in_equation);
                stepSolution += String.format("\n t = (%.3f - %.3f) / %.3f", xf.getMagnitude(), x0.getMagnitude(), v.getMagnitude(), timeValue);
                stepSolution += String.format("\n t = %.3f %s", timeValue, t.getUnit());

                if (t.getMagnitude() < 0) {
                    t.setWarning(true);
                    stepSolution += context.getString(R.string.t_is_negative_warn);
                }
            }
            solved = true;
            return true;
        }
        solved = false;
        return false;
    }
}