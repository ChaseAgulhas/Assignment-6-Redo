package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Potato;

/**
 * Created by Chase on 2016-10-31.
 */

public interface PotatoService {

    void addFood(Context context, Potato food);
    void updateFood(Context context, Potato food);
    
}
