package com.chase.apps.pantry.factories.user;

import com.chase.apps.pantry.domain.user.User;

/**
 * Created by Chase on 2016-10-31.
 */

public class UserFactory {

    private static UserFactory factory = null;

    public static User getInstance(String name, String surname, String dateOfBirth, String userID, String email)
    {
        User user = new User.Builder()
                .userID(userID)
                .name(name)
                .surname(surname)
                .dateOfBirth(dateOfBirth)
                .email(email)
                .build();

        return user;
    }

}
