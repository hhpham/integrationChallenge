package com.hhpham.domain.event;

import com.google.common.base.MoreObjects;

public class Item {

    int quantity;
    String unit;

    public Item() {}

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("quantity", quantity)
                .add("unit", unit)
                .toString();
    }
}
