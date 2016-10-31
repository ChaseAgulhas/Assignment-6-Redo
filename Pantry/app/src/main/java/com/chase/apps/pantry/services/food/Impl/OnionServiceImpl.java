package com.chase.apps.pantry.services.food.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.chase.apps.pantry.domain.food.Onion;
import com.chase.apps.pantry.repository.food.Impl.OnionRepositoryImpl;
import com.chase.apps.pantry.repository.food.OnionRepository;
import com.chase.apps.pantry.services.food.OnionService;

import java.io.Serializable;

/**
 * Created by Chase on 2016-11-01.
 */

public class OnionServiceImpl extends IntentService implements OnionService {

    private static final String ACTION_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String EXTRA_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static OnionServiceImpl service = null;

    private OnionServiceImpl()
    {
        super("OnionServiceImpl");
    }

    public static OnionServiceImpl getInstance()
    {
        if(service == null)
            service = new OnionServiceImpl();

        return service;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null) {
            final String action = intent.getAction();

            if (ACTION_ADD.equals(action)) {
                final Onion onion = (Onion) intent.getSerializableExtra(EXTRA_ADD);
                saveFood(onion);
            } else if (ACTION_UPDATE.equals(action)) {
                final Onion onion = (Onion) intent.getSerializableExtra(EXTRA_UPDATE);
                updateFood(onion);
            }
        }
    }

    private void updateFood(Onion onion)
    {
        //Post and Save local
        OnionRepository onionRepository = new OnionRepositoryImpl(getBaseContext());
        onionRepository.update(onion);
    }

    private void saveFood(Onion onion)
    {
        //Post and Save local
        OnionRepository onionRepository = new OnionRepositoryImpl(getBaseContext());
        onionRepository.save(onion);
    }

    public void deleteAll()
    {
        OnionRepository onionRepository = new OnionRepositoryImpl(getBaseContext());

        try
        {
            onionRepository.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addFood(Context context, Onion onion)
    {
        Intent intent = new Intent(context, OnionServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, (Serializable) onion);
        context.startService(intent);
    }

    @Override
    public void updateFood(Context context, Onion onion)
    {
        Intent intent = new Intent(context, OnionServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, (Serializable) onion);
        context.startService(intent);
    }
}
    



