package com.chase.apps.pantry.conf.databases;

import android.test.AndroidTestCase;

import junit.framework.Assert;

/**
 * Created by Chase on 2016-10-31.
 */

public class DBConstantsTest extends AndroidTestCase{

    public void testDatabaseName()
    {
        Assert.assertEquals(DBConstants.DATABASE_NAME, "Pantry");
    }

    public void testDatabaseVersion()
    {
        Assert.assertEquals(DBConstants.DATABASE_VERSION, 1);
    }


}
