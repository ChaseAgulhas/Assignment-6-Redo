package com.chase.apps.pantry.factories.food;

import com.chase.apps.pantry.domain.food.Meat;

import java.io.Serializable;

/**
 * Created by Chase on 2016-10-31.
 */

public class MeatFactory implements Serializable {

    public static Meat getInternetType(String barcode, String manufacturer, String brandName, String price, String type)
    {
        Meat meat = new Meat.Builder()
                .barcode(barcode)
                .manufacturer(manufacturer)
                .brandName(brandName)
                .price(price)
                .type(type)
                .build();

        return meat;
    }

}
