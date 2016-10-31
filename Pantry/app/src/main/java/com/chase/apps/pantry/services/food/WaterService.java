package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Water;

/**
 * Created by Chase on 2016-10-31.
 */

public interface WaterService {

    void addInternet(Context context, Water internet);
    void updateInternet(Context context, Water internet);
    
}
