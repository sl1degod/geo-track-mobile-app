package com.sl1degod.kursovaya.Models;

public class PostReports {
    int user_id, rep_vio_id, object_id;

    public PostReports(int user_id, int rep_vio_id, int object_id) {
        this.user_id = user_id;
        this.rep_vio_id = rep_vio_id;
        this.object_id = object_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRep_vio_id() {
        return rep_vio_id;
    }

    public void setRep_vio_id(int rep_vio_id) {
        this.rep_vio_id = rep_vio_id;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }
}
