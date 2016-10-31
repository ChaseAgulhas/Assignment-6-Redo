package com.chase.apps.pantry.services.user.Impl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.chase.apps.pantry.domain.user.User;
import com.chase.apps.pantry.repository.user.Impl.UserRepositoryImpl;
import com.chase.apps.pantry.repository.user.UserRepository;
import com.chase.apps.pantry.services.user.UserService;

import java.io.Serializable;

/**
 * Created by Chase on 2016-10-31.
 */

public class UserServiceImpl extends IntentService implements UserService {


    private static final String ACTION_ADD = "com.chase.apps.pantry.services.user.Impl.action.ADD";
    private static final String ACTION_UPDATE = "com.chase.apps.pantry.services.user.Impl.action.UPDATE";

    private static final String EXTRA_ADD = "com.chase.apps.pantry.services.user.Impl.action.ADD";
    private static final String EXTRA_UPDATE = "com.chase.apps.pantry.services.user.Impl.action.UPDATE";

    private static UserServiceImpl service = null;

    private UserServiceImpl()
    {
        super("UserServiceImpl");
    }

    public static UserServiceImpl getInstance()
    {
        if(service == null)
            service = new UserServiceImpl();

        return service;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null) {
            final String action = intent.getAction();

            if (ACTION_ADD.equals(action)) {
                final User user = (User) intent.getSerializableExtra(EXTRA_ADD);
                saveUser(user);
            } else if (ACTION_UPDATE.equals(action)) {
                final User user = (User) intent.getSerializableExtra(EXTRA_UPDATE);
                updateUser(user);
            }
        }
    }

    private void updateUser(User user)
    {
        //Post and Save local
        UserRepository userRepository = new UserRepositoryImpl(getBaseContext());
        userRepository.update(user);
    }

    private void saveUser(User user)
    {
        //Post and Save local
        UserRepository userRepository = new UserRepositoryImpl(getBaseContext());
        userRepository.save(user);
    }

    public void deleteAll()
    {
        UserRepository userRepository = new UserRepositoryImpl(getBaseContext());

        try
        {
            userRepository.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(Context context, User user)
    {
        Intent intent = new Intent(context, UserServiceImpl.class);
        intent.setAction(ACTION_ADD);
        intent.putExtra(EXTRA_ADD, (Serializable) user);
        context.startService(intent);
    }

    @Override
    public void updateUser(Context context, User user)
    {
        Intent intent = new Intent(context, UserServiceImpl.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_UPDATE, (Serializable) user);
        context.startService(intent);
    }

    @Override
    public void deleteUser(Context context, User user)
    {

    }

}
