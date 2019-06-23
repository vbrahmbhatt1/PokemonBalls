package com.oreilly.demo.android.pa.uidemo.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import com.oreilly.demo.android.pa.uidemo.model.Dots;
import com.oreilly.demo.android.pa.uidemo.view.DotView;


public class Alerter {
    View view;
    Dots Dots;

    public Alerter(DotView view, Dots Dots){ this.view = view;this.Dots=Dots; }

    //Screen will display after the game is over
    public void gameOverAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Game Over, it's over 9000!");
        builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { dialog.cancel();}});

        AlertDialog alertDialog = builder.create();
        try { alertDialog.show(); }
        catch (NumberFormatException e) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
            builder1.setTitle("Error");
            builder1.setMessage("no inputs");
            builder1.setPositiveButton("Okay!", null);
            AlertDialog alertDialog1 = builder1.create();
            alertDialog1.show();}}

    //alerts the user on clearing a level
    public void congratsAlert(int level) {
        CharSequence msg = "Level " + level + " cleared";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(view.getContext(), msg, duration);
        try { toast.show(); }
        catch (Exception e) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
            builder1.setTitle("Error");
            builder1.setMessage("no inputs");
            builder1.setPositiveButton("Okay!", null);
            AlertDialog alertDialog1 = builder1.create();
            alertDialog1.show();}}}