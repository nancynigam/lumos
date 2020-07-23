package com.naadworks.lumos.util;

public class Status {

    private StatusType statusType;

    private String message;

    public Status(StatusType statusType) {
        this.statusType = statusType;
        if (statusType.equals(StatusType.SUCCESS))
            this.message = "Operation is successful";
        else if (statusType.equals(StatusType.ERROR))
            this.message = "Operation is unsuccessful";
    }

    public Status(StatusType statusType, String message) {
        this.statusType = statusType;
        this.message = message;
    }

    public Status() {
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusType=" + statusType +
                ", message='" + message + '\'' +
                '}';
    }
}
