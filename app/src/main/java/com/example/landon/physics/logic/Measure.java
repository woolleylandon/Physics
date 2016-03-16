/**
 * Movement with Constant Acceleration
 *
 * This class represents a measure unit.
 * It stores its magnitude and unit.
 *
 * @author Marco Rosas, Winson So, Landon Woolley
 * @version 0.1
 *
 */

package com.example.landon.physics.logic;

public class Measure {

    /**
     * Stores the magnitude of the measure
     */
    private double magnitude;
    /**
     * Stores the unit of the measure as a String
     */
    private String unit;
    /**
     * Tells if the measured has an assumed and not a calculated value
     */
    private boolean assumed;
    /**
     * Tells if the measured was calculated, but could have an error
     */
    private boolean warning;


    /**
     * Magnitude and unit constructor
     */
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
     *
     * @return
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * Sets new value of magnitude
     *
     * @param
     */
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    /**
     * Returns value of unit
     *
     * @return
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets new value of unit
     *
     * @param
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Returns value of assumed
     *
     * @return
     */
    public boolean isAssumed() {
        return assumed;
    }

    /**
     * Sets new value of assumed
     *
     * @param
     */
    public void setAssumed(boolean assumed) {
        this.assumed = assumed;
    }

    /**
     * Returns value of warning
     *
     * @return
     */
    public boolean isWarning() {
        return warning;
    }

    /**
     * Sets new value of warning
     *
     * @param
     */
    public void setWarning(boolean warning) {
        this.warning = warning;
    }
}