package com.chase.apps.pantry.services.food.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.chase.apps.pantry.domain.food.Tomato;
import com.chase.apps.pantry.repository.food.Impl.TomatoRepositoryImpl;
import com.chase.apps.pantry.repository.food.TomatoRepository;
import com.chase.apps.pantry.services.food.TomatoService;

import java.io.Serializable;

/**
 * Created by Chase on 2016-11-01.
 */

public class TomatoServiceImpl extends IntentService implements TomatoService {

    private static final String ACTION_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String EXTRA_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static TomatoServiceImpl service = null;

    private TomatoServiceImpl()
    {
        super("TomatoServiceImpl");
    }

    public static TomatoServiceImpl getInstance()
    {
        if(service == null)
            service = new TomatoServiceImpl();

        return service;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null) {
            final String action = intent.getAction();

            if (ACTION_ADD.equals(action)) {
                final Tomato tomato = (Tomato) intent.getSerializableExtra(EXTRA_ADD);
                saveFood(tomato);
            } else if (ACTION_UPDATE.equals(action)) {
                final Tomato tomato = (Tomato) intent.getSerializableExtra(EXTRA_UPDATE);
                updateFood(tomato);
            }
        }
    }

    private void updateFood(Tomato tomato)
    {
        //Post and Save local
        TomatoRepository tomatoRepository = new TomatoRepositoryImpl(getBaseContext());
        tomatoRepository.update(tomato);
    }

    private void saveFood(Tomato tomato)
    {
        //Post and Save local
        TomatoRepository tomatoRepository = new TomatoRepositoryImpl(getBaseContext());
        tomatoRepository.save(tomato);
    }

    public void deleteAll()
    {
        TomatoRepository tomatoRepository = new TomatoRepositoryImpl(getBaseContext());

        try
        {
            tomatoRepository.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addFood(Context context, Tomato tomato)
    {
        Intent intent = new Intent(context, TomatoServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, (Serializable) tomato);
        context.startService(intent);
    }

    @Override
    public void updateFood(Context context, Tomato tomato)
    {
        Intent intent = new Intent(context, TomatoServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, (Serializable) tomato);
        context.startService(intent);
    }
}
    



