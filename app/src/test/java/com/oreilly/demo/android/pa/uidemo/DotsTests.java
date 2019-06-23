package com.oreilly.demo.android.pa.uidemo;

import com.oreilly.demo.android.pa.uidemo.model.Dots;
import static org.junit.Assert.assertEquals;

public class DotsTests {
    private Dots model;
    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final Dots model) { this.model = model; }

    protected Dots getModel() { return model; }

    public void testInitialNumberOfDots() {
        model.setLevel(1);
        assertEquals(model.getDots().size(), 5);
        model.setLevel(5);
        assertEquals(model.getDots().size(), 25);}}