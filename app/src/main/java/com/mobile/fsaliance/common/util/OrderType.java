package com.mobile.fsaliance.common.util;

import java.io.Serializable;


public class OrderType implements Serializable {
    private String oid;
    private String name;

    public OrderType(String name, String oid) {
        this.oid = oid;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

}
