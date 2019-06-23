package com.oreilly.demo.android.pa.uidemo.model;


//A dot: the coordinates, size, and is it protected or not
public final class Dot {
    private final float x, y;
    private final int diameter;
    private final boolean isitprotected;

    /**
     * @param x horizontal coordinate.
     * @param y vertical coordinate.
     * @param diameter dot diameter.
     */

    public Dot(final float x, final float y, final int diameter, boolean isitprotected) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.isitprotected = isitprotected;
    }

    //@return the horizontal coordinate
    public float getX() { return x; }

    //@return the vertical coordinate
    public float getY() { return y; }

    //@return the dot diameter
    public int getDiameter() { return diameter; }

    //@return the dot is protected or not
    public boolean getProtectedInfo(){ return isitprotected; }}