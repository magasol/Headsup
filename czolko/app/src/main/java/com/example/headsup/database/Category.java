package com.example.headsup.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CATEGORY")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public Integer id;

    @NonNull
    @ColumnInfo(name = "NAME")
    public String name;

    public Category(Integer id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }
}
