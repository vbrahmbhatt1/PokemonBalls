package com.oreilly.demo.android.pa.uidemo.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/** A list of dots. */
public class Dots {
    /** DotChangeListener. */
    public interface DotsChangeListener {
        /** @param dots the dots that changed. */
        void onDotsChange(Dots dots);}

    private int Scored = 0;
    private int Level = 1;
    private int KillCount = 0;
    private boolean GameOver = false;

    //how much time is left in the game
    public long getTimeLeft() { return timeLeft; }

    public void setTimeLeft(long timeLeft) { this.timeLeft = timeLeft; }
    private long timeLeft = 60;

    private final LinkedList<Dot> Dots = new LinkedList<Dot>();
    private final List<Dot> DotList = Collections.unmodifiableList(Dots);
    private DotsChangeListener DotsChangeListener;

    /*The parameter l set the change listener.*/
    public void setDotsChangeListener(DotsChangeListener l) { DotsChangeListener = l; }

    //Returns the last added Dot*/
    public Dot getLastDot() { return (Dots.size() <= 0) ? null : Dots.getLast(); }

    //Returns the list of Dots
    public List<Dot> getDots() { return DotList; }

    /*Here x = horizontal coordinate, y = vertical coordinate, diameter, size of Dot*/
    public void addDot(float x, float y,  int diameter, boolean state) {
        Dots.add(new Dot(x, y, diameter, state));
        notifyListener();}

    //To remove all the Dots.
    public void clearDots() {
        Dots.clear();
        Scored = 0;
        notifyListener();}

    //Returns the size of Dots
    public int countDots() { return Dots.size(); }

    //@Moves the Dots
    public void moveDots(Dot Dot, float newX, float newY, int diameter, boolean state) {
        if (null == getDot(newX, newY)) {
            Dots.remove(Dot);
            addDot(newX, newY, diameter, state);
            notifyListener();}}

    //Returns the index of the Dot
    public Dot getDot(int index) { return Dots.get(index); }

    public Dot getDot(float x, float y) {
        for (Dot Dot : Dots) {
            if (x >= Dot.getX() && y >= Dot.getY()
                    && x <= Dot.getX() + Dot.getDiameter() && y <= Dot.getY() + Dot.getDiameter())
                return Dot;}
        return null;}

    //Returns the score
    public int getScore() { return Scored; }

    //Returns the level
    public int getLevel() { return Level; }

    //set the score
    public void setScore(int Score) { this.Scored = Score; }

    //set the level
    public void setLevel(int level) { this.Level = level; }

    //Returns the KillCount
    public int getKillCount() { return KillCount; }

    //Determines when the game is over
    public void setGameOver() { GameOver = true; }

    //Returns game over
    public boolean getGameOver() { return GameOver; }

    //x=horizontal value of Dot, y=vertical value of Dot
    public void killDot(float x, float y) {
        Dot Dot = getDot(x, y);
        if (Dot != null && !Dot.getProtectedInfo()) {
            Dots.remove(Dot);
            setScore(getScore()+10);
            KillCount += 1;}
        notifyListener();}

    //kills the Dots
    public void setKills(int kills) { this.KillCount = kills; }

    //notifies the Listener
    private void notifyListener() {
        if (null != DotsChangeListener) { DotsChangeListener.onDotsChange(this); }}

    //Returns the delay
    public long getDelay() { return delay; }

    public void setDelay(long delay) { this.delay = delay; }

    long delay;}