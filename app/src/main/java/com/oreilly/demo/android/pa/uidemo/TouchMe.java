package com.oreilly.demo.android.pa.uidemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.TextView;

import com.oreilly.demo.android.pa.uidemo.controller.Alerter;
import com.oreilly.demo.android.pa.uidemo.controller.DotGenerator;
import com.oreilly.demo.android.pa.uidemo.model.Dots;
import com.oreilly.demo.android.pa.uidemo.view.DotView;

import java.util.ArrayList;
import java.util.List;


/*Android UI demo program */

public class TouchMe extends Activity {

    public String TAG = "TouchMe";

    public static final int DOT_DIAMETER = 40;

    //tacks the number of times touched the screen
    private static final class TrackingTouchListener
            implements View.OnTouchListener {
        private final Dots mDots;
        private List<Integer> tracks = new ArrayList<Integer>();

        TrackingTouchListener(Dots Dots) { mDots = Dots; }

        @Override
        public boolean onTouch(View v, MotionEvent evt) {
            int a, index;
            int action = evt.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    index = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                            >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    tracks.add(Integer.valueOf(evt.getPointerId(index)));
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    index = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                            >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    tracks.remove(Integer.valueOf(evt.getPointerId(index)));
                    break;
                case MotionEvent.ACTION_MOVE:
                    a = evt.getHistorySize();
                    for (Integer i : tracks) {
                        index = evt.findPointerIndex(i.intValue());
                        for (int j = 0; j < a; j++) {
                            mDots.killDot(evt.getX(), evt.getY());}}
                    break;
                default:
                    return false;}

            for (Integer i : tracks) {
                index = evt.findPointerIndex(i.intValue());
                killDot(
                        mDots,
                        evt.getX(index),
                        evt.getY(index));}
            return true;}

        private void killDot(Dots Dots, float x, float y) {
            Dots.killDot(x, y);}}

    //Final model
    public Dots DotModel = new Dots();

   //Final view
    DotView DotView;
    int timeleft;

    //dot generator
    public DotGenerator DotGenerator;

    //Start a timer
    CountDownTimer letscdt;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        //creates the view
        setContentView(R.layout.main);

        DotView = (DotView) findViewById(R.id.dots);
        DotView.setDots(DotModel);
        DotView.setOnCreateContextMenuListener(this);
        DotView.setOnTouchListener(new TrackingTouchListener(DotModel));

        //generates new Dot
        DotGenerator = new DotGenerator(DotModel, DotView, 1);
        DotGenerator.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
        System.out.println("task1");

        DotView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean isFocus) {
                if (!isFocus && (null != DotGenerator)) {
                    DotGenerator = null;}
                 else if (isFocus && (null == DotGenerator)) {
                    DotGenerator = new DotGenerator(DotModel, DotView, 1);}}});

        findViewById(R.id.button1).setOnClickListener((final View v) -> {
            final TextView tb3 = (TextView) findViewById(R.id.text3);
            final TextView buttonName = (TextView) findViewById(R.id.button1);
            DotGenerator.setStart(true);
            Alerter  alerter = new Alerter(DotView, DotModel );
            findViewById(R.id.button1).setEnabled(false);
            if(letscdt!=null){
                letscdt.cancel();}
            letscdt = new CountDownTimer(61000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tb3.setText("Time: " + Integer.toString((int) ((millisUntilFinished / 1000) - 1)) + "s");
                    timeleft = (int) ((millisUntilFinished / 1000) - 1);}

                @Override
                public void onFinish() {
                    alerter.gameOverAlert();
                    DotGenerator.setStart(false);
                    DotModel.setLevel(1);
                    DotModel.setScore(0);
                    if(DotModel.getDots().size()!=0){
                        DotModel.clearDots();}
                    DotView.invalidate();
                    findViewById(R.id.button1).setEnabled(true);
                    buttonName.setText("Replay");
                    DotGenerator.setReset(true);}}.start();});

        findViewById(R.id.button2).setOnClickListener((final View v) -> { System.exit(0); });

        final TextView tbb1 = (TextView) findViewById(R.id.text1);
        final TextView tbb2 = (TextView) findViewById(R.id.text2);
        final TextView tbb3 = (TextView) findViewById(R.id.text3);

        DotModel.setDotsChangeListener(new Dots.DotsChangeListener() {
            @Override
            public void onDotsChange(Dots Dots) {
                final int Scored = Dots.getScore();
                final int Level = Dots.getLevel();
                final long time = Dots.getTimeLeft();
                tbb1.setText("Level : " + String.valueOf(Level));
                tbb2.setText("Score : " + String.valueOf(Scored));
                DotView.invalidate();}});}

    //Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                DotModel.clearDots();
                return true;
            default:
                return super.onOptionsItemSelected(item);}}

    //creates and installs the Context Menu
    @Override
    public void onCreateContextMenu(
            ContextMenu menu,
            View v,
            ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, 1, Menu.NONE, "Clear")
                .setAlphabeticShortcut('x');}

    //Response ehe user gets on selecting an option in the context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                DotModel.clearDots();
                return true;
            default:
                ;
        } return false; }

    private boolean start = false;
    public void isstart(boolean foo){
        if (foo)
            start = true;
        else
            start = false;}}
