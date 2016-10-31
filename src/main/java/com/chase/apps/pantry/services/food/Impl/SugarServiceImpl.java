package com.chase.apps.pantry.services.food.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.chase.apps.pantry.domain.food.Sugar;
import com.chase.apps.pantry.repository.food.Impl.SugarRepositoryImpl;
import com.chase.apps.pantry.repository.food.SugarRepository;
import com.chase.apps.pantry.services.food.SugarService;

import java.io.Serializable;

/**
 * Created by Chase on 2016-11-01.
 */

public class SugarServiceImpl extends IntentService implements SugarService {

    private static final String ACTION_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String EXTRA_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static SugarServiceImpl service = null;

    private SugarServiceImpl()
    {
        super("SugarServiceImpl");
    }

    public static SugarServiceImpl getInstance()
    {
        if(service == null)
            service = new SugarServiceImpl();

        return service;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null) {
            final String action = intent.getAction();

            if (ACTION_ADD.equals(action)) {
                final Sugar sugar = (Sugar) intent.getSerializableExtra(EXTRA_ADD);
                saveFood(sugar);
            } else if (ACTION_UPDATE.equals(action)) {
                final Sugar sugar = (Sugar) intent.getSerializableExtra(EXTRA_UPDATE);
                updateFood(sugar);
            }
        }
    }

    private void updateFood(Sugar sugar)
    {
        //Post and Save local
        SugarRepository sugarRepository = new SugarRepositoryImpl(getBaseContext());
        sugarRepository.update(sugar);
    }

    private void saveFood(Sugar sugar)
    {
        //Post and Save local
        SugarRepository sugarRepository = new SugarRepositoryImpl(getBaseContext());
        sugarRepository.save(sugar);
    }

    public void deleteAll()
    {
        SugarRepository sugarRepository = new SugarRepositoryImpl(getBaseContext());

        try
        {
            sugarRepository.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addFood(Context context, Sugar sugar)
    {
        Intent intent = new Intent(context, SugarServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, (Serializable) sugar);
        context.startService(intent);
    }

    @Override
    public void updateFood(Context context, Sugar sugar)
    {
        Intent intent = new Intent(context, SugarServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, (Serializable) sugar);
        context.startService(intent);
    }
}
    



