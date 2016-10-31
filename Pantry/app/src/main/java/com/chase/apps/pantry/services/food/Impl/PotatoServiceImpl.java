package com.chase.apps.pantry.services.food.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.chase.apps.pantry.domain.food.Potato;
import com.chase.apps.pantry.repository.food.Impl.PotatoRepositoryImpl;
import com.chase.apps.pantry.repository.food.PotatoRepository;
import com.chase.apps.pantry.services.food.PotatoService;

import java.io.Serializable;

/**
 * Created by Chase on 2016-11-01.
 */

public class PotatoServiceImpl extends IntentService implements PotatoService {

    private static final String ACTION_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.chase.apps.pantry.services.food.Impl.action.ADD";
    private static final String EXTRA_UPDATE = "com.chase.apps.pantry.services.food.Impl.action.UPDATE";

    private static PotatoServiceImpl service = null;

    private PotatoServiceImpl()
    {
        super("PotatoServiceImpl");
    }

    public static PotatoServiceImpl getInstance()
    {
        if(service == null)
            service = new PotatoServiceImpl();

        return service;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null) {
            final String action = intent.getAction();

            if (ACTION_ADD.equals(action)) {
                final Potato potato = (Potato) intent.getSerializableExtra(EXTRA_ADD);
                saveFood(potato);
            } else if (ACTION_UPDATE.equals(action)) {
                final Potato potato = (Potato) intent.getSerializableExtra(EXTRA_UPDATE);
                updateFood(potato);
            }
        }
    }

    private void updateFood(Potato potato)
    {
        //Post and Save local
        PotatoRepository potatoRepository = new PotatoRepositoryImpl(getBaseContext());
        potatoRepository.update(potato);
    }

    private void saveFood(Potato potato)
    {
        //Post and Save local
        PotatoRepository potatoRepository = new PotatoRepositoryImpl(getBaseContext());
        potatoRepository.save(potato);
    }

    public void deleteAll()
    {
        PotatoRepository potatoRepository = new PotatoRepositoryImpl(getBaseContext());

        try
        {
            potatoRepository.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addFood(Context context, Potato potato)
    {
        Intent intent = new Intent(context, PotatoServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, (Serializable) potato);
        context.startService(intent);
    }

    @Override
    public void updateFood(Context context, Potato potato)
    {
        Intent intent = new Intent(context, PotatoServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, (Serializable) potato);
        context.startService(intent);
    }
}
    



