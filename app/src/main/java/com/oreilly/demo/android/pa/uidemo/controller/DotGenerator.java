package com.oreilly.demo.android.pa.uidemo.controller;

import android.os.AsyncTask;
import com.oreilly.demo.android.pa.uidemo.model.Dots;
import com.oreilly.demo.android.pa.uidemo.view.DotView;
import java.util.concurrent.Semaphore;

//Generate the Dot
public final class DotGenerator extends AsyncTask<Void, Void, Void> {
    Dots Dots;
    DotView ViewAs;
    Alerter alerter;
    boolean StartAs;
    boolean ResetAs;
    boolean SetAs = true;
    boolean LevelsChange = true;
    DotWork DWork;
    Semaphore lock;

    public DotGenerator(Dots Dots, DotView view, int level) {
        this.Dots = Dots;           //generate Dots
        this.ViewAs = view;           //generate view
        Dots.setLevel(level);       //generate level
        alerter = new Alerter(view, Dots );
        DWork = new DotWork(view);
        Dots.setDelay(2000);
        lock = new Semaphore(1);}

    @Override
    protected void onProgressUpdate(final Void... params) {
        if (Dots.getLevel() > 5) {
            alerter.gameOverAlert();
            cancel(true);}

        if (Dots.countDots() == 0 || LevelsChange) {

            //Creates newDot in case no Dot is found
            try { DWork.makeDot(Dots, ViewAs); }
            catch (InterruptedException e) { e.printStackTrace(); }
            if (Dots.getLevel() > 1 && LevelsChange&& SetAs) {
                alerter.congratsAlert(Dots.getLevel() - 1);
                SetAs = false;}
            LevelsChange = false;}
        else {          //move the Dots on UI
            for (int i = 0; i < Dots.countDots(); i++)
                DWork.moveDot(Dots, ViewAs, i);}}

    @Override
    protected Void doInBackground(final Void... params) {
        long time;
        time = System.currentTimeMillis();
        while (!isCancelled()) {
            if (StartAs) {
                publishProgress(null);
                DWork.releaseLock();
            //level is changed if vulnerable Dots are killed
                if (Dots.getScore() == 10 * 5  *   (Dots.getLevel()*2-1))  {
                    Dots.setLevel(Dots.getLevel() + 1);
                    Dots.setDelay(2000 / Dots.getLevel());
                    SetAs = true;
                    LevelsChange = true;
                    Dots.setKills(0);}

            //delay between next move*
                try { Thread.sleep(Dots.getDelay()); }
                catch (InterruptedException e) { e.printStackTrace(); }}}
        return null;}

    @Override
    protected void onCancelled() {
        try {
            lock.acquire(1);
            alerter.gameOverAlert();
            Dots.setLevel(1);
            Dots.setScore(0);
            Dots.setTimeLeft(0);
            Dots.clearDots();
            Dots = null;
            cancel(true);}
         catch(InterruptedException e) { e.printStackTrace(); }
        finally { lock.release(1); }}

    public void setStart(boolean foo){ StartAs = foo; }

    public void setReset(boolean reset){ this.ResetAs = reset; }

    public void res(){
        try { lock.acquire(1); }
        catch(InterruptedException e) { e.printStackTrace(); }
        Dots.setLevel(1);
        Dots.setScore(0);
        ResetAs = false;
        StartAs = false;}}