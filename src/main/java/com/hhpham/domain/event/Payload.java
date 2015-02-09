package com.hhpham.domain.event;

import com.google.common.base.MoreObjects;

public class Payload {

    Company company;
    Order order;
    Configuration configuration;

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

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("company", company)
                .add("order", order)
                .add("configuration", configuration)
                .toString();
    }
}
