package com.example.landon.physics.logic;

public class MCV extends PhysicsSystem implements Solvable {
    private Measure x0;
    private Measure xf;
    private Measure v;
    private Measure t;

    /**
     * Parameter MCV constructor
     */
    public MCV(Measure x0, Measure xf, Measure v, Measure t) {
        this.x0 = x0;
        this.xf = xf;
        this.v = v;
        this.t = t;

        solved = false;
        formulas = "xf = x0 + v*t";
        unknowns = countUnknowns();
        stepSolution = "";
    }

    // getter
    public Measure getX0() {return x0;}
    public Measure getXf() {return xf;}
    public Measure getV()  {return v; }
    public Measure getT()  {return t; }

    public int countUnknowns() {
        int counter = 0;

        if (x0 == null) {counter++;}
        if (xf == null) {counter++;}
        if (v == null)  {counter++;}
        if (t == null)  {counter++;}

        return counter;
    }

    public boolean solveSystem() {
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