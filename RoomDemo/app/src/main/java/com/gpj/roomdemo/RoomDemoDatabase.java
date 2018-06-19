package com.gpj.roomdemo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {StudentEntity.class,ClassEntity.class}, version = 2)
public abstract class RoomDemoDatabase extends RoomDatabase {

    public abstract StudentDao studentDao();

    public abstract ClassDao classDao();
}