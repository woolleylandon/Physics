/**
 * Movement with Constant Acceleration
 *
 * This class represents and MCA system and solves it
 *
 * @author Marco Rosas, Winson So, Landon Woolley
 * @version 0.1
 *
 */

package com.example.landon.physics.logic;
import android.content.Context;
import com.example.landon.physics.R;

public class MCA extends PhysicsSystem implements Solvable {

    /**
     * s0 represents the starting position
     */
    private Measure s0;
    /**
     * sf represents the final position
     */
    private Measure sf;
    /**
     * v0 represents the starting velocity
     */
    private Measure v0;
    /**
     * vf represents the final velocity
     */
    private Measure vf;
    /**
     * a represents the acceleration
     */
    private Measure a;
    /**
     * t represents the time
     */
    private Measure t;

    String xf_formula = "xf = x0 + v(t)";
    String x0_formula = "x0 = xf - v(t)";
    String v_formula = "v = (xf - x0) / t";
    String t_formula = "t = (xf - x0) / v";

    private Context context;

    /**
     * Constructor, takes Measures as parameters. It accepts nulls.
     *
     * @param s0 The starting position
     * @param sf The final position
     * @param v0 The starting velocity
     * @param vf The final velocity
     * @param a  The acceleration
     * @param t  The time
     */
    public MCA(Measure s0, Measure sf, Measure v0, Measure vf, Measure a, Measure t) {
        this.s0 = s0;
        this.sf = sf;
        this.v0 = v0;
        this.vf = vf;
        this.a = a;
        this.t = t;

        solved = false;
        formulas = "sf = s0 + v*t";
        unknowns = countUnknowns();
        stepSolution = "";
    }

    public Measure getS0() {
        return s0;
    }

    public Measure getSf() {
        return sf;
    }

    public Measure getV0() {
        return v0;
    }

    public Measure getVf() {
        return vf;
    }

    public Measure getA() {
        return a;
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

        if (s0 == null) {
            counter++;
        }
        if (sf == null) {
            counter++;
        }
        if (v0 == null) {
            counter++;
        }
        if (vf == null) {
            counter++;
        }
        if (a == null) {
            counter++;
        }
        if (t == null) {
            counter++;
        }

        return counter;
    }

    /**
     * Method that solves the MCA system.
     * Assigns values to those variables that had unknown values.
     * Generates a step by step solution.
     *
     * @return true when the system was solve, false otherwise
     */
    public boolean solveSystem() {

        stepSolution = context.getString(R.string.first_unknowns);
        String variables = "";
        variables += "\ns0 = ";
        variables += s0 == null ? "?" : s0.getMagnitude() + " " + s0.getUnit();
        variables += "\nsf = ";
        variables += sf == null ? "?" : sf.getMagnitude() + " " + sf.getUnit();
        variables += "\nv0 = ";
        variables += v0 == null ? "?" : v0.getMagnitude() + " " + v0.getUnit();
        variables += "\nvf = ";
        variables += vf == null ? "?" : vf.getMagnitude() + " " + vf.getUnit();
        variables += "\na = ";
        variables += a == null ? "?" : a.getMagnitude() + " " + a.getUnit();
        variables += "\nt = ";
        variables += t == null ? "?" : t.getMagnitude() + " " + t.getUnit();

        stepSolution += variables;
        stepSolution += context.getString(R.string.unknowns);
        stepSolution += " " + unknowns;

        // in case s0 and sf are null s0 is assumed to be 0
        if (s0 == null && unknowns > 2) {
            s0 = new Measure(0, "m");
            s0.setAssumed(true);
            unknowns = countUnknowns();
            stepSolution += context.getString(R.string.starting_position_is0);
        }

        // in case v0 and vf are null and a != null v0 or vf is assumed to be 0 depending on accelaration magnitude
        if (v0 == null && vf == null && a != null) {

            stepSolution += context.getString(R.string.missing_vs_mca);

            if (a.getMagnitude() > 0) {
                v0 = new Measure(0, "m/s");
                v0.setAssumed(true);

                stepSolution += context.getString(R.string.positive_a_mca);
                stepSolution += context.getString(R.string.unknowns);
                stepSolution += " " + unknowns;

            } else {
                vf = new Measure(0, "m/s");
                vf.setAssumed(true);

                stepSolution += context.getString(R.string.negative_a_mca);
                stepSolution += context.getString(R.string.unknowns);
                stepSolution += " " + unknowns;
            }
        }

        // cases where there are 2 unknowns
        if (countUnknowns() <= 2) {

            // Unsolvable case (v0 && vf == null)
            if (v0 == null && vf == null) {

                stepSolution += context.getString(R.string.missing_both_velocities);

                return false;
            }

            // CASES 1-8 FINAL/INITIAL POSITION unknown
            if (sf == null || s0 == null) {

                // CASE 1 or 5 calculate TIME
                if (t == null) {
                    double timeValue = (vf.getMagnitude() - v0.getMagnitude()) / a.getMagnitude();
                    t = new Measure(timeValue, "s");
                    stepSolution += context.getString(R.string.t_is);
                    stepSolution += context.getString(R.string.vf_formula);
                    stepSolution += context.getString(R.string.replace_in_equation);
                    stepSolution += context.getString(R.string.missing_both_velocities);
                    stepSolution += String.format("\n t = (%.3f - %.3f) / %.3f", vf.getMagnitude(), v0.getMagnitude(), a.getMagnitude());
                    stepSolution += String.format("\n t = %.3f %s", timeValue, t.getUnit());

                    if (t.getMagnitude() < 0) {
                        stepSolution += context.getString(R.string.t_is_negative_warn);
                        t.setWarning(true);
                    }
                }

                // CASE 2 or 6 calculate INITIAL VELOCITY
                else if (v0 == null) {
                    double initialVelocityValue = vf.getMagnitude() - a.getMagnitude() * t.getMagnitude();
                    v0 = new Measure(initialVelocityValue, "m/s");

                    stepSolution += context.getString(R.string.v0_is);
                    stepSolution += context.getString(R.string.vf_formula);
                    stepSolution += String.format("\n v0 = vf - a(t)");
                    stepSolution += context.getString(R.string.replace_in_equation);
                    stepSolution += String.format("\nv0 = %.3f - %.3f(%.3f)", vf.getMagnitude(), a.getMagnitude(), t.getMagnitude());
                    stepSolution += String.format("\nv0 = %.3f %s", initialVelocityValue, v0.getUnit());
                }

                // CASE 3 or 7 calculate FINAL VELOCITY
                else if (vf == null) {
                    double finalVelocityValue = v0.getMagnitude() + a.getMagnitude() * t.getMagnitude();
                    vf = new Measure(finalVelocityValue, "m/s");
                    stepSolution += context.getString(R.string.vf_is);
                    stepSolution += context.getString(R.string.vf_formula);
                    stepSolution += context.getString(R.string.replace_in_equation);
                    stepSolution += String.format("\nvf = %.3f + %.3f(%.3f)", v0.getMagnitude(), a.getMagnitude(), t.getMagnitude());
                    stepSolution += String.format("\nvf = %.3f %s", finalVelocityValue, vf.getUnit());
                }

                // CASE 4 or 8 calculate ACCELERATION
                else if (a == null) {
                    double accelerationValue = (vf.getMagnitude() - v0.getMagnitude()) / t.getMagnitude();
                    a = new Measure(accelerationValue, "m/s²");
                    stepSolution += context.getString(R.string.a_is);
                    stepSolution += context.getString(R.string.vf_formula);
                    stepSolution += String.format("\na = (vf - v0) / (t)");
                    stepSolution += context.getString(R.string.replace_in_equation);
                    stepSolution += String.format("\na = (%.3f - %.3f) / %.3f", vf.getMagnitude(), v0.getMagnitude(), t.getMagnitude());
                    stepSolution += String.format("\n a = %.3f %s", accelerationValue, a.getUnit());
                }

                // calculates FINAL POSITION if needed
                if (sf == null) {
                    double finalDistanceValue = s0.getMagnitude() + 0.5 * (vf.getMagnitude() + v0.getMagnitude()) * t.getMagnitude();
                    sf = new Measure(finalDistanceValue, "m");
                    stepSolution += context.getString(R.string.sf_is);
                    stepSolution += context.getString(R.string.sf2_formula);
                    stepSolution += context.getString(R.string.replace_in_equation);
                    stepSolution += String.format("\nsf = %.3f  + ½(%.3f + %.3f)%.3f²", s0.getMagnitude(), vf.getMagnitude(), v0.getMagnitude(), t.getMagnitude());
                    stepSolution += String.format("\nsf = %.3f %s", finalDistanceValue, sf.getUnit());
                }

                // calculates INITIAL POSITION if needed
                else if (s0 == null) {
                    double initialDistanceValue = sf.getMagnitude() - 0.5 * (vf.getMagnitude() + v0.getMagnitude()) * t.getMagnitude();
                    s0 = new Measure(initialDistanceValue, "m");
                    stepSolution += context.getString(R.string.s0_is);
                    stepSolution += context.getString(R.string.sf2_formula);
                    stepSolution += String.format("\ns0 = sf - ½(vf + v0)t");
                    stepSolution += context.getString(R.string.replace_in_equation);
                    stepSolution += String.format("\ns0 = %.3f  - ½(%.3f + %.3f)%.3f²", sf.getMagnitude(), vf.getMagnitude(), v0.getMagnitude(), t.getMagnitude());
                    stepSolution += String.format("\ns0 = %.3f %s", initialDistanceValue, s0.getUnit());
                }
            }

            // CASES 9-12 FINAL/INITIAL VELOCITY unknown
            else if (vf == null || v0 == null) {

                if (a == null) {
                    // when acceleration is missing, calculates FINAL VELOCITY if needed
                    if (vf == null) {
                        double finalVelocityValue = 2 * (sf.getMagnitude() - s0.getMagnitude()) / t.getMagnitude() - v0.getMagnitude();
                        vf = new Measure(finalVelocityValue, "m/s");
                        stepSolution += context.getString(R.string.vf_is);
                        stepSolution += context.getString(R.string.sf2_formula);
                        stepSolution += String.format("\nvf = (2(sf - s0)/t ) - v0");
                        stepSolution += context.getString(R.string.replace_in_equation);
                        stepSolution += String.format("\nvf = (2(%.3f - %.3f)/%.3f ) -  %.3f", sf.getMagnitude(), s0.getMagnitude(), t.getMagnitude(), v0.getMagnitude());
                        stepSolution += String.format("\nvf = %.3f %s", finalVelocityValue, vf.getUnit());
                    }

                    // calculates INITIAL VELOCITY if needed
                    else if (v0 == null) {
                        double initialVelocityValue = 2 * (sf.getMagnitude() - s0.getMagnitude()) / t.getMagnitude() - vf.getMagnitude();
                        v0 = new Measure(initialVelocityValue, "m/s");
                        stepSolution += context.getString(R.string.v0_is);
                        stepSolution += context.getString(R.string.sf2_formula);
                        stepSolution += String.format("\nv0 = ( 2(sf - s0)/t ) - vf");
                        stepSolution += context.getString(R.string.replace_in_equation);
                        stepSolution += String.format("\nv0 = ( 2(%.3f - %.3f)/%.3f ) -  %.3f", sf.getMagnitude(), s0.getMagnitude(), t.getMagnitude(), vf.getMagnitude());
                        stepSolution += String.format("\nv0 = %.3f %s", initialVelocityValue, v0.getUnit());
                    }

                    // when acceleration is known
                } else {
                    // calculates FINAL VELOCITY if needed
                    if (vf == null) {
                        double finalVelocitySqrtValue = (Math.pow(v0.getMagnitude(), 2) + 2 * a.getMagnitude() * (sf.getMagnitude() - s0.getMagnitude()));
                        stepSolution += context.getString(R.string.vf_is);
                        stepSolution += context.getString(R.string.vf2_formula);
                        stepSolution += context.getString(R.string.replace_in_equation);
                        stepSolution += String.format("\nvf² = %.3f² + 2(%.3f)(%.3f - %.3f)", v0.getMagnitude(), a.getMagnitude(), sf.getMagnitude(), s0.getMagnitude());
                        stepSolution += String.format("\nvf² = %.3f", finalVelocitySqrtValue);

                        boolean dataWarning = false;
                        if (finalVelocitySqrtValue < 0) {
                            finalVelocitySqrtValue *= -1.0;
                            stepSolution += context.getString(R.string.getting_into_i);
                            dataWarning = true;
                        }

                        double finalVelocityValue = Math.pow(finalVelocitySqrtValue, 0.5);

                        if (a.getMagnitude() < 0) {
                            finalVelocityValue *= -1.0;
                            stepSolution += context.getString(R.string.v_negative_due_to_a);
                        }

                        vf = new Measure(finalVelocityValue, "m/s");
                        vf.setWarning(dataWarning);
                        stepSolution += String.format("\nvf = %.3f %s", finalVelocityValue, vf.getUnit());

                    }

                    // calculates INITIAL VELOCITY if needed
                    else if (v0 == null) {

                        double initialVelocitySqrtValue = (Math.pow(vf.getMagnitude(), 2) - 2 * a.getMagnitude() * (sf.getMagnitude() - s0.getMagnitude()));
                        stepSolution += context.getString(R.string.v0_is);
                        stepSolution += context.getString(R.string.vf2_formula);
                        stepSolution += String.format("\n v0² = vf² - 2a(sf - s0)");
                        stepSolution += context.getString(R.string.replace_in_equation);
                        stepSolution += String.format("\n v0² = %.3f² - 2(%.3f)(%.3f - %.3f)", vf.getMagnitude(), a.getMagnitude(), sf.getMagnitude(), s0.getMagnitude());
                        stepSolution += String.format("\n v0² = %.3f", initialVelocitySqrtValue);

                        boolean dataWarning = false;
                        if (initialVelocitySqrtValue < 0) {
                            initialVelocitySqrtValue *= -1;
                            stepSolution += context.getString(R.string.getting_into_i);
                            dataWarning = true;
                        }

                        double initialVelocityValue = Math.pow(initialVelocitySqrtValue, 0.5);


                        if (a.getMagnitude() < 0) {
                            initialVelocityValue *= -1.0;
                            stepSolution += context.getString(R.string.v_negative_due_to_a);
                        }

                        v0 = new Measure(initialVelocityValue, "m/s");
                        v0.setWarning(dataWarning);
                        stepSolution += String.format("\n v0 = %.3f %s", initialVelocityValue, v0.getUnit());
                    }
                }

                // CASE 9 or 11 calculate TIME
                if (t == null) {
                    double timeValue = (vf.getMagnitude() - v0.getMagnitude()) / a.getMagnitude();
                    t = new Measure(timeValue, "s");

                    stepSolution += context.getString(R.string.t_is);
                    stepSolution += context.getString(R.string.vf_formula);
                    stepSolution += String.format("\n t = (vf - v0) / a");
                    stepSolution += context.getString(R.string.replace_in_equation);
                    stepSolution += String.format("\n\n t = (%.3f - %.3f) / %.3f", vf.getMagnitude(), v0.getMagnitude(), a.getMagnitude());
                    stepSolution += String.format("\n t = %.3f %s", timeValue, t.getUnit());

                    if (t.getMagnitude() < 0) {
                        stepSolution += "@String/t_is_negative_warn";
                        t.setWarning(true);
                    }
                }

                // CASE 10 or 12 calculate ACCELERATION
                else if (a == null) {
                    double accelerationValue = (vf.getMagnitude() - v0.getMagnitude()) / t.getMagnitude();
                    a = new Measure(accelerationValue, "m/s²");

                    stepSolution += context.getString(R.string.a_is);
                    stepSolution += context.getString(R.string.vf_formula);
                    stepSolution += String.format("\n a = (vf - v0) / (t) ");
                    stepSolution += context.getString(R.string.replace_in_equation);
                    stepSolution += String.format("\n\n a = (%.3f - %.3f) / %.3f", vf.getMagnitude(), v0.getMagnitude(), t.getMagnitude());
                    stepSolution += String.format("\n a = %.3f %s", accelerationValue, a.getUnit());
                }
            }

            // case 13 calculate TIME and ACCELERATION
            else if (t == null && a == null) {
                double timeValue = 2 * (sf.getMagnitude() - s0.getMagnitude()) / (vf.getMagnitude() + v0.getMagnitude());
                t = new Measure(timeValue, "s");

                stepSolution += context.getString(R.string.t_is);
                stepSolution += context.getString(R.string.sf2_formula);
                stepSolution += String.format("\n t = 2(sf-s0) / (vf + v0)");
                stepSolution += context.getString(R.string.replace_in_equation);
                stepSolution += String.format("\n t = 2(%.3f - %.3f) / (%.3f + %.3f)", sf.getMagnitude(), s0.getMagnitude(), vf.getMagnitude(), v0.getMagnitude());
                stepSolution += String.format("\n t = %.3f %s", timeValue, t.getUnit());

                if (t.getMagnitude() < 0) {
                    stepSolution += "@String/t_is_negative_warn";
                    t.setWarning(true);
                }

                double accelerationValue = (vf.getMagnitude() - v0.getMagnitude()) / t.getMagnitude();
                a = new Measure(accelerationValue, "m/s²");
                stepSolution += context.getString(R.string.a_is);
                stepSolution += context.getString(R.string.vf_formula);
                stepSolution += String.format("\n a = (vf - v0) / (t)");
                stepSolution += context.getString(R.string.replace_in_equation);
                stepSolution += String.format("\n a = (%.3f - %.3f) / %.3f", vf.getMagnitude(), v0.getMagnitude(), t.getMagnitude());
                stepSolution += String.format("\n a = %.3f %s", accelerationValue, a.getUnit());
                String text = context.getString(R.string.a_is);
                stepSolution += text;
            }

            solved = true;
            return (countUnknowns() == 0);
        } // 2 unknowns cases

        // case not found
        return false;
    }
}