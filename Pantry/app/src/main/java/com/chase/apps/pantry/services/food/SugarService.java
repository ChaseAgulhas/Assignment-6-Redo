package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Sugar;

/**
 * Created by Chase on 2016-10-31.
 */

public interface SugarService {

    void addInternet(Context context, Sugar internet);
    void updateInternet(Context context, Sugar internet);
    
}
