package com.sheep.ezloan.contact.domain.model;

public enum ContractStatus {

    WAITING("waiting"), IN_PROGRESS("in_progress"), COMPLETED("completed"), CANCELED("canceled");

    private final String status;

    ContractStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return this.status;
    }

}
