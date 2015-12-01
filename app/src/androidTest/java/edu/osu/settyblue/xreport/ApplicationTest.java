package edu.osu.settyblue.xreport;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;


import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() {

        super(Application.class);
        assertEquals("not matching",true,true);
    }

    @Test
    public void LoginTest() {
        //register a new user.


    }
}