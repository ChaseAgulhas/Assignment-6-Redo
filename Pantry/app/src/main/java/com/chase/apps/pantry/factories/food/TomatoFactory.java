package com.chase.apps.pantry.factories.food;

import com.chase.apps.pantry.domain.food.Tomato;

import java.io.Serializable;

/**
 * Created by Chase on 2016-10-31.
 */

public class TomatoFactory implements Serializable {

    public static Tomato getInternetType(String barcode, String manufacturer, String brandName, String price, String type)
    {
        Tomato tomato = new Tomato.Builder()
                .barcode(barcode)
                .manufacturer(manufacturer)
                .brandName(brandName)
                .price(price)
                .type(type)
                .build();

        return tomato;
    }

}
