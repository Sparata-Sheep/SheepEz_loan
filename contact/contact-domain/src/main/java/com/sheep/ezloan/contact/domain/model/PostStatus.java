package com.sheep.ezloan.contact.domain.model;

public enum PostStatus {

    OPEN("open"), IN_PROGRESS("in_progress"), COMPLETED("completed");

    private final String status;

    PostStatus(String status) {
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
