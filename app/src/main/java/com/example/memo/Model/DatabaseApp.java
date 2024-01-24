package com.example.memo.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class, Categorie.class},version = 1)
abstract public class DatabaseApp extends RoomDatabase {

    public static DatabaseApp instance;
    public abstract AppDao appDao();

    public static synchronized DatabaseApp getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),DatabaseApp.class,"App_Note")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}
