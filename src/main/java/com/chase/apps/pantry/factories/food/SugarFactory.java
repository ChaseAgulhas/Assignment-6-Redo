package com.chase.apps.pantry.factories.food;

import com.chase.apps.pantry.domain.food.Sugar;

import java.io.Serializable;

/**
 * Created by Chase on 2016-10-31.
 */

public class SugarFactory implements Serializable {

    public static Sugar getInternetType(String barcode, String manufacturer, String brandName, String price, String type)
    {
        Sugar sugar = new Sugar.Builder()
                .barcode(barcode)
                .manufacturer(manufacturer)
                .brandName(brandName)
                .price(price)
                .type(type)
                .build();

        return sugar;
    }

}
