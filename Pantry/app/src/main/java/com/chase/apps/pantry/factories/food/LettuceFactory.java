package com.chase.apps.pantry.factories.food;

import com.chase.apps.pantry.domain.food.Lettuce;

import java.io.Serializable;

/**
 * Created by Chase on 2016-10-31.
 */

public class LettuceFactory implements Serializable {

    public static Lettuce getInternetType(String barcode, String manufacturer, String brandName, String price, String type)
    {
        Lettuce lettuce = new Lettuce.Builder()
                .barcode(barcode)
                .manufacturer(manufacturer)
                .brandName(brandName)
                .price(price)
                .type(type)
                .build();

        return lettuce;
    }

}
