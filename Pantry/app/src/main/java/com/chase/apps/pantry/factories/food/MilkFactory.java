package com.chase.apps.pantry.factories.food;

import com.chase.apps.pantry.domain.food.Milk;

import java.io.Serializable;

/**
 * Created by Chase on 2016-10-31.
 */

public class MilkFactory implements Serializable {

    public static Milk getInternetType(String barcode, String manufacturer, String brandName, String price, String type)
    {
        Milk milk = new Milk.Builder()
                .barcode(barcode)
                .manufacturer(manufacturer)
                .brandName(brandName)
                .price(price)
                .type(type)
                .build();

        return milk;
    }

}
