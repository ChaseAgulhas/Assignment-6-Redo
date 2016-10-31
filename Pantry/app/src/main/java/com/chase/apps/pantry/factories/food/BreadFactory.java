package com.chase.apps.pantry.factories.food;

import com.chase.apps.pantry.domain.food.Bread;

import java.io.Serializable;

/**
 * Created by Chase on 2016-10-31.
 */

public class BreadFactory implements Serializable{

    public static Bread getInternetType(String barcode, String manufacturer, String brandName, String price, String type)
    {
        Bread bread = new Bread.Builder()
                .barcode(barcode)
                .manufacturer(manufacturer)
                .brandName(brandName)
                .price(price)
                .type(type)
                .build();

        return bread;
    }

}
