package com.gpj.roomdemo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ClassDao {
    @Query("SELECT * FROM tb_class")
    List<ClassEntity> getAll();

    @Query("SELECT * FROM tb_class WHERE id IN (:ids)")
    List<ClassEntity> getAllByIds(long[] ids);

    @Insert
    void insert(ClassEntity... entities);

    @Delete
    void delete(ClassEntity entity);

    @Update
    void update(ClassEntity entity);
}
