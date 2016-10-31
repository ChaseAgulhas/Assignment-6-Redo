package com.chase.apps.pantry.factories.food;

import com.chase.apps.pantry.domain.food.Potato;

import java.io.Serializable;

/**
 * Created by Chase on 2016-10-31.
 */

public class PotatoFactory implements Serializable {

    public static Potato getInternetType(String barcode, String manufacturer, String brandName, String price, String type)
    {
        Potato potato = new Potato.Builder()
                .barcode(barcode)
                .manufacturer(manufacturer)
                .brandName(brandName)
                .price(price)
                .type(type)
                .build();

        return potato;
    }

}
