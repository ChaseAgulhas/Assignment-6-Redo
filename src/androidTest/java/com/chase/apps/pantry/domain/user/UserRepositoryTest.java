package com.chase.apps.pantry.domain.user;

import android.content.Context;
import android.test.AndroidTestCase;

import com.chase.apps.pantry.repository.user.Impl.UserRepositoryImpl;
import com.chase.apps.pantry.repository.user.UserRepository;

import junit.framework.Assert;

import java.util.Set;

/**
 * Created by Chase on 2016-10-31.
 */

public class UserRepositoryTest extends AndroidTestCase {


    private String id, surname;

    public void testCreateReadUpdateDelete() throws Exception {
        Context context = getContext();
        UserRepository userRepository = new UserRepositoryImpl(context);

        // CREATE
        User user = new User.Builder()
                .userID("1")
                .name("Chase")
                .surname("Agulhas")
                .dateOfBirth("19920614")
                .email("chase@gmail.com")
                .build();

        User insertedEntity = userRepository.save(user);
        id = insertedEntity.getUserID();
        surname = insertedEntity.getSurname();
        Assert.assertNotNull(insertedEntity);

        // READ ALL
        Set<User> userSet = userRepository.findAll();
        Assert.assertTrue(userSet.size() > 0);

        // READ ENTITY
        User entity = userRepository.findById(id, surname);
        Assert.assertNotNull(entity);

        // UPDATE ENTITY
        User updateEntity = new User.Builder()
                .copy(entity)
                .name("taylor")
                .build();
        userRepository.update(updateEntity);
        User newEntity = userRepository.findById(id, surname);
        Assert.assertEquals("Chase", newEntity.getName());

        // DELETE ENTITY
        userRepository.delete(updateEntity);
        User deletedEntity = userRepository.findById(id, surname);
        Assert.assertNull(deletedEntity);


        // DELETE ALL
        userRepository.deleteAll();
        Set<User> deletedUsers = userRepository.findAll();
        Assert.assertTrue(deletedUsers.size() == 0);


    }


}
