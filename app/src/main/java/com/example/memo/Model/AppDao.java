package com.example.memo.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDao {
    @Insert
    void addCategorie(Categorie categorie);
    @Insert
    void addNote(Note note);
    @Query("SELECT * FROM categorieTable")
    LiveData<List<Categorie>> getCategories();
    @Query("SELECT * FROM NoteTable where CategoryId= :id")
    LiveData<List<Note>> getNotes(long id);
    @Query("SELECT * FROM NoteTable")
    LiveData<List<Note>> getAllNotes();
    @Query("UPDATE NoteTable Set title=:newTitle,text=:newText  where id=:idNt AND CategoryId=:idCat ")
    void update(String newTitle,String newText,long idNt,long idCat);
    @Query("Delete FROM NoteTable where id=:idNt AND CategoryId=:idCat ")
    void deletee(long idNt,long idCat);
    @Query("Delete FROM categorieTable where id=:idCat")
    void deleteCt(long idCat);
    @Query("Delete from  NoteTable where CategoryId=:idCat ")
    void deleteNotes(long idCat);
}
