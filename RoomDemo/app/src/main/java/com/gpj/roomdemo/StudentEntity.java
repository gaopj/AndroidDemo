package com.gpj.roomdemo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


//指示数据表实体类
@Entity(tableName = "tb_student",//定义表名
        indices = @Index(value = {"name", "sex"}, unique = true),//定义索引
        foreignKeys = {@ForeignKey(entity = ClassEntity.class,
                parentColumns = "id",
                childColumns = "class_id")})//定义外键
public class StudentEntity {
    @PrimaryKey //定义主键
    private long id;
    @ColumnInfo(name = "name")//定义数据表中的字段名
    private String name;
    @ColumnInfo(name = "sex")
    private int sex;
    @Ignore//指示Room需要忽略的字段或方法
    private String ignoreText;
    @ColumnInfo(name = "class_id")
    private long class_id;

    //setter and getter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIgnoreText() {
        return ignoreText;
    }

    public void setIgnoreText(String ignoreText) {
        this.ignoreText = ignoreText;
    }

    public long getClass_id() {
        return class_id;
    }

    public void setClass_id(long class_id) {
        this.class_id = class_id;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", ignoreText='" + ignoreText + '\'' +
                ", class_id='" + class_id + '\'' +
                '}';
    }


}