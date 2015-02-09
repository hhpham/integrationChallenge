package com.hhpham.domain.event;

import com.google.common.base.MoreObjects;

public class Order {

    String pricingDuration;
    String editionCode;
    Item item;

    public Order() {}

    public String getEditionCode() {
        return editionCode;
    }

    public void setEditionCode(String editionCode) {
        this.editionCode = editionCode;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getPricingDuration() {
        return pricingDuration;
    }

    public void setPricingDuration(String pricingDuration) {
        this.pricingDuration = pricingDuration;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pricingDuration", pricingDuration)
                .add("editionCode", editionCode)
                .add("item", item)
                .toString();
    }
}
