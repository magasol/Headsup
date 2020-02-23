package com.example.headsup.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GuessDao {
    @Query("SELECT * FROM GUESS WHERE CATEGORY_ID=:categoryId")
    List<Guess> getAllByCategory(Integer categoryId);

    @Insert
    void insertAll(Guess... guesses);

    @Delete
    void delete(Guess guess);
}
