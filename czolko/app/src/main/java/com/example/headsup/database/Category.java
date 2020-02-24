package com.example.headsup.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "CATEGORY")
public class Category implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public Integer id;

    @NonNull
    @ColumnInfo(name = "NAME")
    public String name;

    @NonNull
    @ColumnInfo(name = "IMAGE_NAME")
    public String imageName;

    public Category(Integer id, @NonNull String name, @NonNull String imageName) {
        this.id = id;
        this.name = name;
        this.imageName = imageName;
    }
}
