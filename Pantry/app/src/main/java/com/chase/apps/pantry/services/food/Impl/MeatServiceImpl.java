package com.chase.apps.pantry.services.food.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.chase.apps.pantry.domain.food.Meat;
import com.chase.apps.pantry.repository.food.Impl.MeatRepositoryImpl;
import com.chase.apps.pantry.repository.food.MeatRepository;
import com.chase.apps.pantry.services.food.MeatService;

import java.io.Serializable;

/**
 * Created by Chase on 2016-11-01.
 */

public class MeatServiceImpl extends IntentService implements MeatService {

    private static final String ACTION_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String EXTRA_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static MeatServiceImpl service = null;

    private MeatServiceImpl()
    {
        super("MeatServiceImpl");
    }

    public static MeatServiceImpl getInstance()
    {
        if(service == null)
            service = new MeatServiceImpl();

        return service;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null) {
            final String action = intent.getAction();

            if (ACTION_ADD.equals(action)) {
                final Meat meat = (Meat) intent.getSerializableExtra(EXTRA_ADD);
                saveFood(meat);
            } else if (ACTION_UPDATE.equals(action)) {
                final Meat meat = (Meat) intent.getSerializableExtra(EXTRA_UPDATE);
                updateFood(meat);
            }
        }
    }

    private void updateFood(Meat meat)
    {
        //Post and Save local
        MeatRepository meatRepository = new MeatRepositoryImpl(getBaseContext());
        meatRepository.update(meat);
    }

    private void saveFood(Meat meat)
    {
        //Post and Save local
        MeatRepository meatRepository = new MeatRepositoryImpl(getBaseContext());
        meatRepository.save(meat);
    }

    public void deleteAll()
    {
        MeatRepository meatRepository = new MeatRepositoryImpl(getBaseContext());

        try
        {
            meatRepository.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addFood(Context context, Meat meat)
    {
        Intent intent = new Intent(context, MeatServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, (Serializable) meat);
        context.startService(intent);
    }

    @Override
    public void updateFood(Context context, Meat meat)
    {
        Intent intent = new Intent(context, MeatServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, (Serializable) meat);
        context.startService(intent);
    }
}
    


