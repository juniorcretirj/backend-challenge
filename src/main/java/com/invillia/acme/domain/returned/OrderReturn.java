package com.invillia.acme.domain.returned;

import java.util.UUID;

public class OrderReturn {
    private UUID id;

    private UUID storeId;

    private String address;

    private String status;

    public OrderReturn(UUID id, UUID storeId, String address, String status) {
        this.id = id;
        this.storeId = storeId;
        this.address = address;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public void setStoreId(UUID storeId) {
        this.storeId = storeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
