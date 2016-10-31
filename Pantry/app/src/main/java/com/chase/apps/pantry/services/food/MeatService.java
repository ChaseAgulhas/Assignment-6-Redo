package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Meat;

/**
 * Created by Chase on 2016-10-31.
 */

public interface MeatService {

    void addInternet(Context context, Meat internet);
    void updateInternet(Context context, Meat internet);
    
}
