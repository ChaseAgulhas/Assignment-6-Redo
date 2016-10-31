package com.chase.apps.pantry.factories.food;

import com.chase.apps.pantry.domain.food.Onion;

import java.io.Serializable;

/**
 * Created by Chase on 2016-10-31.
 */

public class OnionFactory implements Serializable {

    public static Onion getInternetType(String barcode, String manufacturer, String brandName, String price, String type)
    {
        Onion onion = new Onion.Builder()
                .barcode(barcode)
                .manufacturer(manufacturer)
                .brandName(brandName)
                .price(price)
                .type(type)
                .build();

        return onion;
    }

}
