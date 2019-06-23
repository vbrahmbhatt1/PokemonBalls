package com.oreilly.demo.android.pa.uidemo;

import android.app.KeyguardManager;
import android.test.ActivityInstrumentationTestCase2;

public class TouchMeTests extends ActivityInstrumentationTestCase2<TouchMe> {
    public TouchMeTests() { super(TouchMe.class); }

    public void testLaunchSuccessful() {
        TouchMe activity = getActivity();
        assertNotNull(activity);}

    public void testOrientationLockAtRotation( ) {
        TouchMe activity = getActivity();
        KeyguardManager km = (KeyguardManager) getActivity().getSystemService(activity.KEYGUARD_SERVICE);
        boolean locked = km.inKeyguardRestrictedInputMode();
        assertTrue(locked);}}