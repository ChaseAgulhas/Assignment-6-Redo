package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Milk;


/**
 * Created by Chase on 2016-10-31.
 */

public interface MilkService {

    void addFood(Context context, Milk food);
    void updateFood(Context context, Milk food);
    
}
