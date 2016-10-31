package com.chase.apps.pantry.services.food;

import android.content.Context;

import com.chase.apps.pantry.domain.food.Lettuce;

/**
 * Created by Chase on 2016-10-31.
 */

public interface LettuceService {

    void addInternet(Context context, Lettuce internet);
    void updateInternet(Context context, Lettuce internet);
}
