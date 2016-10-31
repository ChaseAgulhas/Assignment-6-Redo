package com.chase.apps.pantry.services.food.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.chase.apps.pantry.domain.food.Milk;
import com.chase.apps.pantry.repository.food.Impl.MilkRepositoryImpl;
import com.chase.apps.pantry.repository.food.MilkRepository;
import com.chase.apps.pantry.services.food.MilkService;

import java.io.Serializable;

/**
 * Created by Chase on 2016-11-01.
 */

public class MilkServiceImpl extends IntentService implements MilkService {
    


    private static final String ACTION_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String EXTRA_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static MilkServiceImpl service = null;

    private MilkServiceImpl()
    {
        super("MilkServiceImpl");
    }

    public static MilkServiceImpl getInstance()
    {
        if(service == null)
            service = new MilkServiceImpl();

        return service;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null) {
            final String action = intent.getAction();

            if (ACTION_ADD.equals(action)) {
                final Milk milk = (Milk) intent.getSerializableExtra(EXTRA_ADD);
                saveFood(milk);
            } else if (ACTION_UPDATE.equals(action)) {
                final Milk milk = (Milk) intent.getSerializableExtra(EXTRA_UPDATE);
                updateFood(milk);
            }
        }
    }

    private void updateFood(Milk milk)
    {
        //Post and Save local
        MilkRepository milkRepository = new MilkRepositoryImpl(getBaseContext());
        milkRepository.update(milk);
    }

    private void saveFood(Milk milk)
    {
        //Post and Save local
        MilkRepository milkRepository = new MilkRepositoryImpl(getBaseContext());
        milkRepository.save(milk);
    }

    public void deleteAll()
    {
        MilkRepository milkRepository = new MilkRepositoryImpl(getBaseContext());

        try
        {
            milkRepository.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addFood(Context context, Milk milk)
    {
        Intent intent = new Intent(context, MilkServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, (Serializable) milk);
        context.startService(intent);
    }

    @Override
    public void updateFood(Context context, Milk milk)
    {
        Intent intent = new Intent(context, MilkServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, (Serializable) milk);
        context.startService(intent);
    }
}
    



