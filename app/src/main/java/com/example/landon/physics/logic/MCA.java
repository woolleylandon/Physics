package com.example.landon.physics.logic;

public class MCA extends PhysicsSystem implements Solvable {
    private Measure s0;
    private Measure sf;
    private Measure v0;
    private Measure vf;
    private Measure a;
    private Measure t;

    // formulas
    String sf_formula = "sf = s₀ + v₀t + ½at²";
    String sf2_formula = "sf = s₀ + ½(vf + v₀)t";
    String vf_formula = "vf = v₀ + at";
    String vf2_formula = "vf² = v₀² + 2a(sf - s₀)";

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

    public MCA(Measure s0, Measure sf, Measure v0, Measure vf, Measure a, Measure t) {
        this.s0 = s0;
        this.sf = sf;
        this.v0 = v0;
        this.vf = vf;
        this.a = a;
        this.t = t;

        solved = false;
        formulas = sf_formula + "\n" + sf2_formula + "\n" + vf_formula + "\n" + vf2_formula;
        unknowns = countUnknowns();
        //stepSolution = "";
    }

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

    public boolean solveSystem() {

        //stepSolution = "@String/first_unknowns";

        String variables = "";

        variables += "\ns₀ = ";
        variables += s0 == null ? "?" : s0.getMagnitude() + " " + s0.getUnit();
        variables += "\nsf = ";
        variables += sf == null ? "?" : sf.getMagnitude() + " " + sf.getUnit();
        variables += "\nv₀ = ";
        variables += v0 == null ? "?" : v0.getMagnitude() + " " + v0.getUnit();
        variables += "\nvf = ";
        variables += vf == null ? "?" : vf.getMagnitude() + " " + vf.getUnit();
        variables += "\na = ";
        variables += a == null ? "?" : a.getMagnitude() + " " + a.getUnit();
        variables += "\nt = ";
        variables += t == null ? "?" : t.getMagnitude() + " " + t.getUnit();

        //stepSolution += variables;
        //stepSolution += "@String/unknowns";
        //stepSolution += unknowns;

        // in case s0 and sf are null s0 is assumed to be 0
        if (s0 == null && unknowns > 2) {

            s0 = new Measure(0, "m");
            s0.setAssumed(true);

            // step by step solution
            //stepSolution += "@String/starting_position_is0";
            //stepSolution += "@String/unknowns";
            //stepSolution += unknowns;
        }

        // in case v0 and vf are null and a != null v0 or vf is assumed to be 0 depending on accelaration magnitude
        if (v0 == null && vf == null && a != null) {

            // step by step solution
            //stepSolution += "@String/missing_Vs_mca";

            if (a.getMagnitude() > 0) {
                v0 = new Measure(0, "m/s");
                v0.setAssumed(true);

                //stepSolution += "@String/positive_a_mca";
                //stepSolution += "@String/unknowns";
                //stepSolution += unknowns;

            } else {
                vf = new Measure(0, "m/s");
                vf.setAssumed(true);

                //stepSolution += "@String/negative_a_mca";
                //stepSolution += "@String/unknowns";
                //stepSolution += unknowns;
            }
        }

        // cases where there are 2 unknowns
        if (countUnknowns() <= 2) {

            // Unsolvable case (v0 && vf == null)
            if (v0 == null && vf == null) {

                // step by step solution
                //stepSolution += "@String/missing_both_velocities";

                return false;
            }

            // CASES 1-8 FINAL/INITIAL POSITION unknown
            if (sf == null || s0 == null) {

                // CASE 1 or 5 calculate TIME
                if (t == null) {

                    // t = (vf - v0) / a
                    double timeValue = (vf.getMagnitude() - v0.getMagnitude()) / a.getMagnitude();
                    t = new Measure(timeValue, "s");

                    // step by step solution
                    //stepSolution += "@String/t_is";
                    //stepSolution += "\n" + vf_formula;
                    //stepSolution += "\n t = (vf - v₀) / a";
                    //stepSolution += "@String/replace_in_equation";
                    //stepSolution += String.format("\n t = (%.3f - %.3f) / %.3f", vf.getMagnitude(), v0.getMagnitude(), a.getMagnitude());
                    //stepSolution += String.format("\n t = %.3f %s", timeValue, t.getUnit());

                    if (t.getMagnitude() < 0) {
                        //stepSolution += "@String/t_is_negative_warn";
                        t.setWarning(true);
                    }
                }

                // CASE 2 or 6 calculate INITIAL VELOCITY
                else if (v0 == null) {

                    // v0 = vf - a(t)
                    double initialVelocityValue = vf.getMagnitude() - a.getMagnitude() * t.getMagnitude();
                    v0 = new Measure(initialVelocityValue, "m/s");

                    // step by step solution
                    //stepSolution += "@String/v0_is";
                    //stepSolution += "\n" + vf_formula;
                    //stepSolution += "\n v₀ = vf - a(t)";
                    //stepSolution += "@String/replace_in_equation";
                    //stepSolution += String.format("\n\n v₀ = %.3f - %.3f(%.3f)", vf.getMagnitude(), a.getMagnitude(), t.getMagnitude());
                    //stepSolution += String.format( "\n v₀ = %.3f %s", initialVelocityValue, v0.getUnit());
                }

                // CASE 3 or 7 calculate FINAL VELOCITY
                else if (vf == null) {

                    // vf = v0 + a(t)
                    double finalVelocityValue = v0.getMagnitude() + a.getMagnitude() * t.getMagnitude();

                    vf = new Measure(finalVelocityValue, "m/s");

                    // step by step solution
                    //stepSolution += "@String/vf_is";
                    //stepSolution += "\n" + vf_formula;
                    //stepSolution += "@String/replace_in_equation";
                    //stepSolution += String.format( "\n\n vf = %.3f + %.3f(%.3f)", v0.getMagnitude(), a.getMagnitude(), t.getMagnitude());
                    //stepSolution += String.format( "\n vf = %.3f %@", finalVelocityValue, vf.getUnit());
                }

                // CASE 4 or 8 calculate ACCELERATION
                else if (a == null) {

                    // a = (vf - v0) / (t)
                    double accelerationValue = (vf.getMagnitude() - v0.getMagnitude()) / t.getMagnitude();

                    a = new Measure(accelerationValue, "m/s²");

                    // step by step solution
                    //stepSolution += "@string/a_is";
                    //stepSolution += "\n" + vf_formula;
                    //stepSolution += "\n a = (vf - v₀) / (t)";
                    //stepSolution += "@String/replace_in_equation";
                    //stepSolution += String.format( "\n\n a = (%.3f - %.3f) / %.3f", vf.getMagnitude(), v0.getMagnitude(), t.getMagnitude());
                    //stepSolution += String.format( "\n a = %.3f %@", accelerationValue, a.getUnit());
                }

                // calculates FINAL POSITION if needed
                if (sf == null) {

                    // sf = s0 + 0.5(vf + v0)t
                    double finalDistanceValue = s0.getMagnitude() + 0.5 * (vf.getMagnitude() + v0.getMagnitude()) * t.getMagnitude();

                    sf = new Measure(finalDistanceValue, "m");

                    // description for step by step solution
                    //stepSolution += "@String/sf_is";
                    //stepSolution += "\n" + sf2_formula;
                    //stepSolution += "@String/replace_in_equation";
                    //stepSolution += String.format( "\n sf = %.3f  + ½(%.3f + %.3f)%.3f²", s0.getMagnitude(), vf.getMagnitude(), v0.getMagnitude(), t.getMagnitude());
                    //stepSolution += String.format( "\n sf = %.3f %@", finalDistanceValue, sf.getUnit());
                }
                // calculates INITIAL POSITION if needed
                else if (s0 == null) {
                    // s0 = sf - 0.5(vf + v0)t
                    double initialDistanceValue = sf.getMagnitude() - 0.5 * (vf.getMagnitude() + v0.getMagnitude()) * t.getMagnitude();

                    s0 = new Measure(initialDistanceValue, "m");

                    // description for step by step solution
                    //stepSolution += "@String/s0_is";
                    //stepSolution += "\n" + sf2_formula;
                    //stepSolution += "\n s₀ = sf - ½(vf + v₀)t";
                    //stepSolution += "@String/replace_in_equation";
                    //stepSolution += String.format( "\n s₀ = %.3f  - ½(%.3f + %.3f)%.3f²", sf.getMagnitude(), vf.getMagnitude(), v0.getMagnitude(), t.getMagnitude());
                    //stepSolution += String.format( "\n s₀ = %.3f %@", initialDistanceValue, s0.getUnit());
                }
            }

            // CASES 9-12 FINAL/INITIAL VELOCITY unknown
            else if (vf == null || v0 == null) {

                if (a == null) {
                    // when acceleration is missing

                    // calculates FINAL VELOCITY if needed
                    if (vf == null) {
                        // vf = ( 2(sf - s0)/t ) - v0
                        double finalVelocityValue = 2 * (sf.getMagnitude() - s0.getMagnitude()) / t.getMagnitude() - v0.getMagnitude();

                        vf = new Measure(finalVelocityValue, "m/s");

                        // step by step solution
                        //stepSolution += "@String/vf_is";
                        //stepSolution += "\n" + sf2_formula;
                        //stepSolution += "\n vf = ( 2(sf - s₀)/t ) - v0";
                        //stepSolution += "@String/replace_in_equation";
                        //stepSolution += String.format( "\n vf = ( 2(%.3f - %.3f)/%.3f ) -  %.3f", sf.getMagnitude(), s0.getMagnitude(), t.getMagnitude(), v0.getMagnitude());
                        //stepSolution += String.format( "\n vf = %.3f %@", finalVelocityValue, vf.getUnit());

                    }

                    // calculates INITIAL VELOCITY if needed
                    else if (v0 == null) {
                        // v0 = ( 2(sf - s0)/t ) - vf
                        double initialVelocityValue = 2 * (sf.getMagnitude() - s0.getMagnitude()) / t.getMagnitude() - vf.getMagnitude();

                        v0 = new Measure(initialVelocityValue, "m/s");

                        // step by step solution
                        //stepSolution += "@String/v0_is";
                        //stepSolution += "\n" + sf2_formula;
                        //stepSolution += "\n v₀ = ( 2(sf - s₀)/t ) - vf";
                        //stepSolution += "@String/replace_in_equation";
                        //stepSolution += String.format( "\n v₀ = ( 2(%.3f - %.3f)/%.3f ) -  %.3f", sf.getMagnitude(), s0.getMagnitude(), t.getMagnitude(), vf.getMagnitude());
                        //stepSolution += String.format( "\n v₀ = %.3f %@", initialVelocityValue, v0.getUnit());

                    }

                    // when acceleration is known
                } else {
                    // calculates FINAL VELOCITY if needed
                    if (vf == null) {

                        // vf = sqrt( v0² + 2a(sf - s0) )
                        double finalVelocitySqrtValue = (Math.pow(v0.getMagnitude(), 2) + 2 * a.getMagnitude() * (sf.getMagnitude() - s0.getMagnitude()));

                        // step by step solution
                        //stepSolution += "@String/vf_is";
                        //stepSolution += "\n" + vf2_formula;
                        //stepSolution += "@String/replace_in_equation";
                        //stepSolution += String.format( "\n vf² = %.3f² + 2(%.3f)(%.3f - %.3f)", v0.getMagnitude(), a.getMagnitude(), sf.getMagnitude(), s0.getMagnitude());
                        //stepSolution += String.format( "\n vf² = %.3f", finalVelocitySqrtValue);

                        boolean dataWarning = false;
                        if (finalVelocitySqrtValue < 0) {
                            finalVelocitySqrtValue *= -1.0;

                            //stepSolution += "@String/getting_into_i";
                            dataWarning = true;
                        }

                        double finalVelocityValue = Math.pow(finalVelocitySqrtValue, 0.5);

                        if (a.getMagnitude() < 0) {
                            finalVelocityValue *= -1.0;
                            //stepSolution += "@String/v_negative_due_to_a";
                        }

                        vf = new Measure(finalVelocityValue, "m/s");
                        vf.setWarning(dataWarning);

                        // step by step solution
                        //stepSolution += String.format( "\n vf = %.3f %@", finalVelocityValue, vf.getUnit());

                    }

                    // calculates INITIAL VELOCITY if needed
                    else if (v0 == null) {

                        // v0 = sqrt( v0² - 2a(sf - s0) )
                        double initialVelocitySqrtValue = (Math.pow(vf.getMagnitude(), 2) - 2 * a.getMagnitude() * (sf.getMagnitude() - s0.getMagnitude()));

                        //stepSolution += "@String/v0_is";
                        //stepSolution += "\n" + vf2_formula;
                        //stepSolution += "\n v₀² = vf² - 2a(sf - s₀)";
                        //stepSolution += "@String/replace_in_equation";
                        //stepSolution += String.format( "\n v₀² = %.3f² - 2(%.3f)(%.3f - %.3f)", vf.getMagnitude(), a.getMagnitude(), sf.getMagnitude(), s0.getMagnitude());
                        //stepSolution += String.format( "\n v₀² = %.3f", initialVelocitySqrtValue);

                        boolean dataWarning = false;
                        if (initialVelocitySqrtValue < 0) {
                            initialVelocitySqrtValue *= -1;

                            //stepSolution += "@String/getting_into_i";
                            dataWarning = true;
                        }

                        double initialVelocityValue = Math.pow(initialVelocitySqrtValue, 0.5);

                        if (a.getMagnitude() < 0) {
                            initialVelocityValue *= -1.0;
                            //stepSolution += "@String/v_negative_due_to_a";
                        }

                        v0 = new Measure(initialVelocityValue, "m/s");
                        v0.setWarning(dataWarning);

                        // step by step solution
                        //stepSolution += String.format( "\n v0 = %.3f %@", initialVelocityValue, v0.getUnit());
                    }
                }

                // CASE 9 or 11 calculate TIME
                if (t == null) {
                    // t = (vf - v0) / a
                    double timeValue = (vf.getMagnitude() - v0.getMagnitude()) / a.getMagnitude();

                    t = new Measure(timeValue, "s");

                    // step by step solution
                    //stepSolution += "@String/t_is";
                    //stepSolution += "\n" + vf_formula;
                    //stepSolution += "\n t = (vf - v0) / a";
                    //stepSolution += "@String/replace_in_equation";
                    //stepSolution += String.format( "\n\n t = (%.3f - %.3f) / %.3f", vf.getMagnitude(), v0.getMagnitude(), a.getMagnitude());
                    //stepSolution += String.format( "\n t = %.3f %@", timeValue, t.getUnit());

                    if (t.getMagnitude() < 0) {
                        //stepSolution += "@String/t_is_negative_warn";
                        t.setWarning(true);
                    }
                }

                // CASE 10 or 12 calculate ACCELERATION
                else if (a == null) {
                    // a = (vf - v0) / (t)
                    double accelerationValue = (vf.getMagnitude() - v0.getMagnitude()) / t.getMagnitude();

                    a = new Measure(accelerationValue, "m/s²");

                    // step by step solution
                    //stepSolution += "@String/a_is";
                    //stepSolution += "\n" + vf_formula;
                    //stepSolution += "\n a = (vf - v0) / (t)";
                    //stepSolution += "@String/replace_in_equation";
                    //stepSolution += String.format( "\n\n a = (%.3f - %.3f) / %.3f", vf.getMagnitude(), v0.getMagnitude(), t.getMagnitude());
                    //stepSolution += String.format( "\n a = %.3f %@", accelerationValue, a.getUnit());
                }
            }

            // case 13 calculate TIME and ACCELERATION
            else if (t == null && a == null) {

                // t = 2(sf-s0) / (vf + v0)
                double timeValue = 2 * (sf.getMagnitude() - s0.getMagnitude()) / (vf.getMagnitude() + v0.getMagnitude());

                t = new Measure(timeValue, "s");

                // step by step solution
                //stepSolution += "@String/t_is";
                //stepSolution += "\n" + sf2_formula;
                //stepSolution += "\n t = 2(sf-s0) / (vf + v0)";
                //stepSolution += "@String/replace_in_equation";
                //stepSolution += String.format( "\n\n t = 2(%.3f - %.3f) / (%.3f + %.3f)", sf.getMagnitude(), s0.getMagnitude(), vf.getMagnitude(), v0.getMagnitude());
                //stepSolution += String.format( "\n t = %.3f %@", timeValue, t.getUnit());

                if (t.getMagnitude() < 0) {
                    //stepSolution += "@String/t_is_negative_warn";
                    t.setWarning(true);
                }

                // a = (vf - v0) / (t)
                double accelerationValue = (vf.getMagnitude() - v0.getMagnitude()) / t.getMagnitude();

                a = new Measure(accelerationValue, "m/s²");

                // step by step solution
                //stepSolution += "@String/a_is";
                //stepSolution += "\n" + vf_formula;
                //stepSolution += "\n a = (vf - v0) / (t)";
                //stepSolution += "@String/replace_in_equation";
                //stepSolution += String.format( "\n\n a = (%.3f - %.3f) / %.3f", vf.getMagnitude(), v0.getMagnitude(), t.getMagnitude());
                //stepSolution += String.format( "\n a = %.3f %@", accelerationValue, a.getUnit());
                stepSolution += "@string/a_is";
            }

            // debugging
            System.out.println(stepSolution);

            return (countUnknowns() == 0);

        } // 2 unknowns cases

        // case not found
        return false;
    }
}