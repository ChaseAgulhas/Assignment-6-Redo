package com.chase.apps.pantry.services;

import android.content.Context;
import android.test.AndroidTestCase;

import com.chase.apps.pantry.domain.user.User;
import com.chase.apps.pantry.repository.user.Impl.UserRepositoryImpl;
import com.chase.apps.pantry.repository.user.UserRepository;
import com.chase.apps.pantry.services.user.Impl.UserServiceImpl;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by Chase on 2016-10-31.
 */

public class UserServiceTest extends AndroidTestCase {

    public void testUserInsert() throws Exception {

        UserServiceImpl userService = UserServiceImpl.getInstance();
        Context context = getContext();
        UserRepository userRepository = new UserRepositoryImpl(context);

        User user = new User.Builder()
                .userID("1")
                .name("Chase")
                .surname("Agulhas")
                .dateOfBirth("19920614")
                .email("chase@gmail.com")
                .build();

        userService.addUser(this.mContext, user);

        Thread.sleep(5000);
        // READ ALL USERS
        Set<User> userSet = userRepository.findAll();
        Assert.assertTrue(userSet.size() > 0);
    }

}
