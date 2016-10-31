package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Sugar;

/**
 * Created by Chase on 2016-10-31.
 */

public interface SugarService {

    void addFood(Context context, Sugar food);
    void updateFood(Context context, Sugar food);
    
}
