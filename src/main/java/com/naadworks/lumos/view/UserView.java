package com.naadworks.lumos.view;

import com.naadworks.lumos.entry.UserEntry;
import com.naadworks.lumos.util.Status;

import java.util.List;

public class UserView {

    private Status status;

    private List<UserEntry> data;

    public UserView(Status status, List<UserEntry> data) {
        this.status = status;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<UserEntry> getData() {
        return data;
    }

    public void setData(List<UserEntry> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserView{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
