package com.example.preparelectures;

import com.google.firebase.Timestamp;

public class lectures {
    int roomNo;
    Timestamp time;
    Boolean marked;

    public lectures(int roomNo, Timestamp time, Boolean marked) {
        this.roomNo = roomNo;
        this.marked = marked;
        this.time = time;
    }

    public lectures() {
    }

    public int getRoomNo() {
        return roomNo;
    }

    public Boolean getMarked() {
        return marked;
    }

    public void setMarked(Boolean marked) {
        this.marked = marked;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }
}