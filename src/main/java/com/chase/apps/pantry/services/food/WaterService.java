package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Water;

/**
 * Created by Chase on 2016-10-31.
 */

public interface WaterService {

    void addFood(Context context, Water food);
    void updateFood(Context context, Water food);
    
}
