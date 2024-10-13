package com.sheep.ezloan.contact.domain.model;

public enum LoanType {

    GIVE("give"), TAKE("take");

    private String type;

    LoanType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}
