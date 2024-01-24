package com.example.memo.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NoteTable")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String title;
    public String text;
    public long CategoryId;

    public Note(String title, String text,long CategoryId) {
        this.title = title;
        this.text = text;
        this.CategoryId=CategoryId;
    }
}
