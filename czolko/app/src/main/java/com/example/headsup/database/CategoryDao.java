package com.example.headsup.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM CATEGORY")
    List<Category> getAll();

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);
}
