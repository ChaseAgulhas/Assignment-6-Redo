package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Milk;


/**
 * Created by Chase on 2016-10-31.
 */

public interface MilkService {

    void addInternet(Context context, Milk internet);
    void updateInternet(Context context, Milk internet);
    
}
