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

	/**
	* Returns value of x0
	* @return
	*/
	public Measure getX0() {
		return x0;
	}

	/**
	* Sets new value of x0
	* @param
	*/
	public void setX0(Measure x0) {
		this.x0 = x0;
	}

	/**
	* Returns value of xf
	* @return
	*/
	public Measure getXf() {
		return xf;
	}

	/**
	* Sets new value of xf
	* @param
	*/
	public void setXf(Measure xf) {
		this.xf = xf;
	}

	/**
	* Returns value of v
	* @return
	*/
	public Measure getV() {
		return v;
	}

	/**
	* Sets new value of v
	* @param
	*/
	public void setV(Measure v) {
		this.v = v;
	}

	/**
	* Returns value of t
	* @return
	*/
	public Measure getT() {
		return t;
	}

	/**
	* Sets new value of t
	* @param
	*/
	public void setT(Measure t) {
		this.t = t;
	}

  public int countUnknowns(){
    int counter = 0;

    if (x0 == null) { counter++; };
    if (xf == null) { counter++; };
    if (v == null) { counter++; };
    if (t == null) { counter++; };

    return counter;
  }

  public boolean solveSystem() {
	  if(countUnknowns() == 1){
		  if (x0 == null){
			  double finalX0 = xf.getMagnitude() - v.getMagnitude() * t.getMagnitude();
			  x0 = new Measure(finalX0, "m");
			  return true;
		  }
		  if (xf == null) {
			  double finalXf = x0.getMagnitude() + v.getMagnitude() * t.getMagnitude();
			  xf = new Measure(finalXf, "m");
			  return true;
		  }
		  if (v == null) {
			  double finalV = (xf.getMagnitude() - x0.getMagnitude()) / t.getMagnitude();
			  v = new Measure(finalV, "m/s");
			  return true;
		  }
		  if (t == null) {
			  double finalT = (xf.getMagnitude() - x0.getMagnitude()) / v.getMagnitude();
			  t = new Measure(finalT, "s");
			  return true;
		  }
		  else
			  return false;
	  }
	  return false;
  }

}
