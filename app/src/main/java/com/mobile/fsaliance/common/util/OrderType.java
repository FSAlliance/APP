package com.mobile.fsaliance.common.util;

import java.io.Serializable;


public class OrderType implements Serializable {
    private int typeId;
    private String name;

    public OrderType(String name, int typeId) {
        this.typeId = typeId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
