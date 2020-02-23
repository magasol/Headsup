package com.example.headsup.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Category.class, Guess.class}, exportSchema = false, version = 1)
public abstract class HeadsupDatabase extends RoomDatabase {

    private static final String DB_NAME = "headsup";
    private static HeadsupDatabase instance;

    public static synchronized  HeadsupDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), HeadsupDatabase.class,
                    DB_NAME)
                    .createFromAsset("headsup.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract CategoryDao categoryDao();
    public abstract GuessDao guessDao();
}
