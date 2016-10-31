package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Bread;

/**
 * Created by Chase on 2016-10-31.
 */

public interface BreadService {

    void addFood(Context context, Bread food);
    void updateFood(Context context, Bread food);

}
