package com.example.headsup.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GuessDao {
    @Query("SELECT GUESS.ID, GUESS.NAME, GUESS.CATEGORY_ID " +
            "FROM GUESS JOIN CATEGORY ON CATEGORY.ID = GUESS.CATEGORY_ID " +
            "WHERE CATEGORY.NAME=:categoryName " +
            "ORDER BY RANDOM() LIMIT 30")
    List<Guess> getRandomByCategory(String categoryName);

    @Insert
    void insertAll(Guess... guesses);

    @Delete
    void delete(Guess guess);
}
