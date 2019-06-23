package com.oreilly.demo.android.pa.uidemo;

import com.oreilly.demo.android.pa.uidemo.controller.DotWork;
import com.oreilly.demo.android.pa.uidemo.model.Dot;
import com.oreilly.demo.android.pa.uidemo.model.Dots;
import com.oreilly.demo.android.pa.uidemo.view.DotView;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DotTests {

    private Dot dotModel;
    private Dots dotsModel;
    private DotWork dotWork;
    private DotView dotView;

    // A poke ball that moves autonomously should only move into their adjacent cells at each move
    public void testMoveDotIntoAdjacentLocations() {
        // Get a random dot, and compare the x-axis and y-axis before and after the move
        Random rand = new Random();
        int numberOfDots = dotsModel.getDots().size();
        int randomDotIndex = rand.nextInt(numberOfDots);
        Dot randomDot = dotsModel.getDot(randomDotIndex);

        float oldX = randomDot.getX();
        float oldY = randomDot.getY();
        dotWork.moveDot(dotsModel, dotView, randomDotIndex);

        float newX = randomDot.getX();
        float newY = randomDot.getY();
        int cellSize = dotView.getCellSize();

        assertTrue((newX > (oldX - cellSize)) && (newX < (oldX + cellSize)));
        assertTrue((newY > (oldY - cellSize)) && (newY < (oldY + cellSize)));}

    // A poke ball can only be clicked when it's in the vulnerable state
    public void tesPokeKillableState() {
        Random rand = new Random();
        int numberOfDots = dotsModel.getDots().size();
        int randomDotIndex = rand.nextInt(numberOfDots);
        Dot safeDot = dotsModel.getDot(randomDotIndex);

        // Find a poke ball in protected state and try to catch it
        if (safeDot.getProtectedInfo() != true) {
            randomDotIndex = rand.nextInt(numberOfDots);
            safeDot = dotsModel.getDot(randomDotIndex);}

        assertTrue(safeDot.getProtectedInfo());
        dotsModel.killDot(safeDot.getX(), safeDot.getY());
        assertNotNull(safeDot);

        // Find a poke ball  in unprotected state and try to catch it
        randomDotIndex = rand.nextInt(numberOfDots);
        Dot vulnerableddot = dotsModel.getDot(randomDotIndex);

        if (safeDot.getProtectedInfo() == true) {
            randomDotIndex = rand.nextInt(numberOfDots);
            vulnerableddot = dotsModel.getDot(randomDotIndex);}

        assertFalse(vulnerableddot.getProtectedInfo());
        dotsModel.killDot(safeDot.getX(), safeDot.getY());
        assertNull(vulnerableddot);}}
