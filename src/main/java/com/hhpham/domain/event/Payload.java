package com.hhpham.domain.event;

import com.google.common.base.MoreObjects;

public class Payload {

    Company company;
    Order order;

    public Payload() {}

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("company", company)
                .add("order", order)
                .toString();
    }
}
