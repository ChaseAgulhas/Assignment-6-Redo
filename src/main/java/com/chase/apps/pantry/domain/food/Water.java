package com.chase.apps.pantry.domain.food;

import java.io.Serializable;

/**
 * Created by Chase on 2016-10-31.
 */

public class Water implements Serializable {

    private String barcode, manufacturer, brandName;
    String price;
    String type = "FOOD";
    private Bread nextFoodType;

    private Water(){}

    private Water(Builder builder)
    {
        this.barcode = builder.barcode;
        this.manufacturer = builder.manufacturer;
        this.brandName = builder.brandName;
        this.type = builder.type;
        this.price = builder.price;
    }

    public String getBarcode()
    {
        return barcode;
    }
    public String getManufacturer()
    {
        return manufacturer;
    }
    public String getBrandName()
    {
        return brandName;
    }
    public String getPrice()
    {
        return price;
    }

    public String getType(){return type;}
    public String toString()
    {
        return "IP address: " + getBarcode() + "\nmanufacturer: " + getManufacturer()+ "\nPlan name: " + getBrandName()
                + "\nPrice: " + getPrice() +  "\nType: " + getType();
    }

    public static class Builder
    {
        private String barcode, manufacturer, brandName;
        String price, dataAllowance, type;

        public Builder barcode(String value)
        {
            this.barcode = value;
            return this;
        }

        public Builder manufacturer(String value)
        {
            this.manufacturer = value;
            return this;
        }

        public Builder brandName(String value)
        {
            this.brandName = value;
            return this;
        }

        public Builder price(String value)
        {
            this.price = value;
            return this;
        }

        public Builder type(String value)
        {
            this.type = value;
            return this;
        }

        public Builder copy(Water value)
        {
            this.barcode = value.barcode;
            this.manufacturer = value.manufacturer;
            this.type = value.type;
            this.brandName = value.brandName;
            this.price = value.price;

            return this;
        }

        public Water build()
        {
            return new Water(this);
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;

        Water internet = (Water) o;

        return barcode != null ? barcode.equals(internet.barcode) : internet.barcode == null;
    }

    @Override
    public int hashCode()
    {
        return barcode != null ? barcode.hashCode() : 0;
    }

}

