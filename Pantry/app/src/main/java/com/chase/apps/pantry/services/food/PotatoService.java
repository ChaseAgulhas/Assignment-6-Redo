package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Potato;

/**
 * Created by Chase on 2016-10-31.
 */

public interface PotatoService {

    void addInternet(Context context, Potato internet);
    void updateInternet(Context context, Potato internet);
    
}
