package com.example.landon.physics.logic;

public class Measure {
  private double magnitude;
  private String unit;
  private boolean assumed;
  private boolean warning;

  /**
	* Default empty Measure constructor
	*/
	public Measure() {
		super();
	}

  public Measure(double magnitude, String unit) {
		this(magnitude, unit, false, false);
	}

	/**
	* Default Measure constructor
	*/
	public Measure(double magnitude, String unit, boolean assumed, boolean warning) {
		super();
		this.magnitude = magnitude;
		this.unit = unit;
		this.assumed = assumed;
		this.warning = warning;
	}

	/**
	* Returns value of magnitude
	* @return
	*/
	public double getMagnitude() {
		return magnitude;
	}

	/**
	* Sets new value of magnitude
	* @param
	*/
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	/**
	* Returns value of unit
	* @return
	*/
	public String getUnit() {
		return unit;
	}

	/**
	* Sets new value of unit
	* @param
	*/
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	* Returns value of assumed
	* @return
	*/
	public boolean isAssumed() {
		return assumed;
	}

	/**
	* Sets new value of assumed
	* @param
	*/
	public void setAssumed(boolean assumed) {
		this.assumed = assumed;
	}

	/**
	* Returns value of warning
	* @return
	*/
	public boolean isWarning() {
		return warning;
	}

	/**
	* Sets new value of warning
	* @param
	*/
	public void setWarning(boolean warning) {
		this.warning = warning;
	}

}
