package com.example.headsup.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "GUESS", foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "ID",
        childColumns = "CATEGORY_ID"))
public class Guess {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public Integer id;

    @NonNull
    @ColumnInfo(name = "NAME")
    public String name;

    @NonNull
    @ColumnInfo(name = "CATEGORY_ID")
    public Integer categoryId;

    public Guess(Integer id, @NonNull String name, @NonNull Integer categoryId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
    }
}
