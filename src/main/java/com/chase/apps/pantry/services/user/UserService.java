package com.chase.apps.pantry.services.user;

import android.content.Context;

import com.chase.apps.pantry.domain.user.User;

/**
 * Created by Chase on 2016-10-31.
 */

public interface UserService {

    void addUser(Context context, User user);
    void updateUser(Context context, User user);
    void deleteUser(Context context, User user);

}
