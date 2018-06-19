package com.gpj.roomdemo;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RoomDemoDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//         database = Room.databaseBuilder(getApplicationContext(),
//                RoomDemoDatabase.class, "database_name")
//                .addCallback(new RoomDatabase.Callback() {
//                    //第一次创建数据库时调用，但是在创建所有表之后调用的
//                    @Override
//                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        super.onCreate(db);
//
//
//                    }
//
//                    //当数据库被打开时调用
//                    @Override
//                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
//                        super.onOpen(db);
//                    }
//                })
//                .allowMainThreadQueries()//允许在主线程查询数据
//                .addMigrations()//迁移数据库使用
//                .fallbackToDestructiveMigration()//迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
//                .build();

         database = Room.databaseBuilder(getApplicationContext(),
                RoomDemoDatabase.class, "database_name")
                 .fallbackToDestructiveMigration()
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClassEntity classEntity = new ClassEntity();
                classEntity.setId(6);
                database.classDao().insert(classEntity);
                StudentEntity studentEntity = new StudentEntity();
                studentEntity.setId(13);
                studentEntity.setName("gpj2");
                studentEntity.setIgnoreText("qwerq");
                studentEntity.setClass_id(3);
                studentEntity.setSex(2);
                database.studentDao().insert(studentEntity);
                List<StudentEntity> studentEntities = database.studentDao().getAll();
                for(int i=0;i<studentEntities.size();i++){
                    Log.d("jpg",studentEntities.get(i).toString());
                }
                database.close();

            }
        }).start();

    }
}
