package com.example.memo.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categorieTable")
public class Categorie {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public Categorie(String name) {
        this.name = name;
    }
}
