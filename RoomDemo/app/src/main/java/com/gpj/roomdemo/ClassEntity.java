package com.gpj.roomdemo;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "tb_class")
public class ClassEntity {

    @PrimaryKey
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClassEntity{" +
                "id=" + id +
                '}';
    }
}