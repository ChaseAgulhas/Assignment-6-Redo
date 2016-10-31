package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Onion;

/**
 * Created by Chase on 2016-10-31.
 */

public interface OnionService {

    void addInternet(Context context, Onion internet);
    void updateInternet(Context context, Onion internet);
    
}
