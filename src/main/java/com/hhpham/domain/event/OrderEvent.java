package com.hhpham.domain.event;

import com.google.common.base.MoreObjects;

public class OrderEvent {

    String type;
    Marketplace marketplace;
    Creator creator;
    String flag;
    Payload payload;

    public OrderEvent() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("marketplace", marketplace)
                .add("creator", creator)
                .add("flag", flag)
                .add("payload", payload)
                .toString();
    }
}
