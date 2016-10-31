package com.chase.apps.pantry.services.food.Impl;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chase.apps.pantry.conf.databases.DBConstants;
import com.chase.apps.pantry.domain.food.Lettuce;
import com.chase.apps.pantry.repository.food.Impl.LettuceRepositoryImpl;
import com.chase.apps.pantry.repository.food.LettuceRepository;
import com.chase.apps.pantry.services.food.LettuceService;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chase on 2016-11-01.
 */

public class LettuceServiceImpl extends IntentService implements LettuceService {


    private static final String ACTION_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String EXTRA_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static LettuceServiceImpl service = null;

    private LettuceServiceImpl()
    {
        super("LettuceServiceImpl");
    }

    public static LettuceServiceImpl getInstance()
    {
        if(service == null)
            service = new LettuceServiceImpl();

        return service;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null) {
            final String action = intent.getAction();

            if (ACTION_ADD.equals(action)) {
                final Lettuce lettuce = (Lettuce) intent.getSerializableExtra(EXTRA_ADD);
                saveFood(lettuce);
            } else if (ACTION_UPDATE.equals(action)) {
                final Lettuce lettuce = (Lettuce) intent.getSerializableExtra(EXTRA_UPDATE);
                updateFood(lettuce);
            }
        }
    }

    private void updateFood(Lettuce lettuce)
    {
        //Post and Save local
        LettuceRepository lettuceRepository = new LettuceRepositoryImpl(getBaseContext());
        lettuceRepository.update(lettuce);
    }

    private void saveFood(Lettuce lettuce)
    {
        //Post and Save local
        LettuceRepository lettuceRepository = new LettuceRepositoryImpl(getBaseContext());
        lettuceRepository.save(lettuce);
    }

    public void deleteAll()
    {
        LettuceRepository lettuceRepository = new LettuceRepositoryImpl(getBaseContext());

        try
        {
            lettuceRepository.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addFood(Context context, Lettuce lettuce)
    {
        Intent intent = new Intent(context, LettuceServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, (Serializable) lettuce);
        context.startService(intent);
    }

    @Override
    public void updateFood(Context context, Lettuce lettuce)
    {
        Intent intent = new Intent(context, LettuceServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, (Serializable) lettuce);
        context.startService(intent);
    }
}
    

