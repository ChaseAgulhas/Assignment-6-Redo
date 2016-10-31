package com.chase.apps.pantry.factories.food;

import com.chase.apps.pantry.domain.food.Water;

import java.io.Serializable;

/**
 * Created by Chase on 2016-10-31.
 */

public class WaterFactory implements Serializable {

    public static Water getInternetType(String barcode, String manufacturer, String brandName, String price, String type)
    {
        Water water = new Water.Builder()
                .barcode(barcode)
                .manufacturer(manufacturer)
                .brandName(brandName)
                .price(price)
                .type(type)
                .build();

        return water;
    }

}
