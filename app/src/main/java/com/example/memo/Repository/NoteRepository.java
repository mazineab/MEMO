package com.example.memo.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.memo.Model.AppDao;
import com.example.memo.Model.Categorie;
import com.example.memo.Model.DatabaseApp;
import com.example.memo.Model.Note;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteRepository {
    public AppDao appDao;
    private Executor executor = Executors.newSingleThreadExecutor(); // Using a single-thread executor
    LiveData<List<Categorie>> getCategorie;
    LiveData<List<Note>> getNotes;
    LiveData<List<Note>> getAllNotes;
    public NoteRepository(Application application){
        DatabaseApp databaseApp=DatabaseApp.getInstance(application);
        appDao=databaseApp.appDao();
        getCategorie=appDao.getCategories();

    }
    public void addCategorie(Categorie categorie){
        executor.execute(()->{
            appDao.addCategorie(categorie);
        });
    }
    public void addNote(Note note){
        executor.execute(()->{
            appDao.addNote(note);
        });
    }
    public LiveData<List<Categorie>> getCategories(){
        return getCategorie;
    }
    public LiveData<List<Note>>getNotes(long id){
        getNotes=appDao.getNotes(id);
        return getNotes;
    }
    public LiveData<List<Note>> getAllNotes(){
        getAllNotes= appDao.getAllNotes();
        return getAllNotes;
    }
    public void update(String newTitle,String newText,long idNt,long idCat){
        executor.execute(()->{
            appDao.update(newTitle,newText,idNt,idCat);
        });

    }
    public void deletee(long idNt,long idCat){
        executor.execute(()->{
            appDao.deletee(idNt,idCat);
        });

    }
    public void deleteCt(long idCat){
        executor.execute(()->{
            appDao.deleteCt(idCat);
        });
    }
    public void deleteNotes(long idCat){
        executor.execute(()->{
            appDao.deleteNotes(idCat);
        });
    }


}
