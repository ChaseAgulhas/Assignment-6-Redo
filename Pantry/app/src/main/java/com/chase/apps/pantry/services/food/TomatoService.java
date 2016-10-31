package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Tomato;

/**
 * Created by Chase on 2016-10-31.
 */

public interface TomatoService {

    void addInternet(Context context, Tomato internet);
    void updateInternet(Context context, Tomato internet);
    
}
