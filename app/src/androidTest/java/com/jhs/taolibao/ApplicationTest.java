package com.jhs.taolibao;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public void Test() {
        Log.i("dds", "Test");
    }

    public void Test1() {
        Log.i("dds", "Test1");
    }
}