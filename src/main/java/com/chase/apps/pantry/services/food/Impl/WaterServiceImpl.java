package com.chase.apps.pantry.services.food.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.chase.apps.pantry.domain.food.Water;
import com.chase.apps.pantry.repository.food.Impl.WaterRepositoryImpl;
import com.chase.apps.pantry.repository.food.WaterRepository;
import com.chase.apps.pantry.services.food.WaterService;

import java.io.Serializable;

/**
 * Created by Chase on 2016-11-01.
 */

public class WaterServiceImpl extends IntentService implements WaterService {

    private static final String ACTION_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String EXTRA_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static WaterServiceImpl service = null;

    private WaterServiceImpl()
    {
        super("WaterServiceImpl");
    }

    public static WaterServiceImpl getInstance()
    {
        if(service == null)
            service = new WaterServiceImpl();

        return service;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null) {
            final String action = intent.getAction();

            if (ACTION_ADD.equals(action)) {
                final Water water = (Water) intent.getSerializableExtra(EXTRA_ADD);
                saveFood(water);
            } else if (ACTION_UPDATE.equals(action)) {
                final Water water = (Water) intent.getSerializableExtra(EXTRA_UPDATE);
                updateFood(water);
            }
        }
    }

    private void updateFood(Water water)
    {
        //Post and Save local
        WaterRepository waterRepository = new WaterRepositoryImpl(getBaseContext());
        waterRepository.update(water);
    }

    private void saveFood(Water water)
    {
        //Post and Save local
        WaterRepository waterRepository = new WaterRepositoryImpl(getBaseContext());
        waterRepository.save(water);
    }

    public void deleteAll()
    {
        WaterRepository waterRepository = new WaterRepositoryImpl(getBaseContext());

        try
        {
            waterRepository.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addFood(Context context, Water water)
    {
        Intent intent = new Intent(context, WaterServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, (Serializable) water);
        context.startService(intent);
    }

    @Override
    public void updateFood(Context context, Water water)
    {
        Intent intent = new Intent(context, WaterServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, (Serializable) water);
        context.startService(intent);
    }
}
    



