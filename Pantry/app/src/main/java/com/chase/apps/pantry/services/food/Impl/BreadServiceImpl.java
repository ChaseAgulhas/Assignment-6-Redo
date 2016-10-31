package com.chase.apps.pantry.services.food.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.chase.apps.pantry.domain.food.Bread;
import com.chase.apps.pantry.repository.food.BreadRepository;
import com.chase.apps.pantry.repository.food.Impl.BreadRepositoryImpl;
import com.chase.apps.pantry.services.food.BreadService;

import java.io.Serializable;

/**
 * Created by Chase on 2016-11-01.
 */

public class BreadServiceImpl extends IntentService implements BreadService {


        private static final String ACTION_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
        private static final String ACTION_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

        private static final String EXTRA_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
        private static final String EXTRA_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

        private static BreadServiceImpl service = null;

        private BreadServiceImpl()
        {
            super("BreadServiceImpl");
        }

    public static BreadServiceImpl getInstance()
    {
        if(service == null)
            service = new BreadServiceImpl();

        return service;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null) {
            final String action = intent.getAction();

            if (ACTION_ADD.equals(action)) {
                final Bread bread = (Bread) intent.getSerializableExtra(EXTRA_ADD);
                saveFood(bread);
            } else if (ACTION_UPDATE.equals(action)) {
                final Bread bread = (Bread) intent.getSerializableExtra(EXTRA_UPDATE);
                updateFood(bread);
            }
        }
    }

    private void updateFood(Bread bread)
    {
        //Post and Save local
        BreadRepository breadRepository = new BreadRepositoryImpl(getBaseContext());
        breadRepository.update(bread);
    }

    private void saveFood(Bread bread)
    {
        //Post and Save local
        BreadRepository breadRepository = new BreadRepositoryImpl(getBaseContext());
        breadRepository.save(bread);
    }

    public void deleteAll()
    {
        BreadRepository breadRepository = new BreadRepositoryImpl(getBaseContext());

        try
        {
            breadRepository.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addFood(Context context, Bread bread)
    {
        Intent intent = new Intent(context, BreadServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, (Serializable) bread);
        context.startService(intent);
    }

    @Override
    public void updateFood(Context context, Bread bread)
    {
        Intent intent = new Intent(context, BreadServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, (Serializable) bread);
        context.startService(intent);
    }
}


