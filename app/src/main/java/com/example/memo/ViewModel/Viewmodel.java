package com.example.memo.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.memo.Model.Categorie;
import com.example.memo.Repository.NoteRepository;
import com.example.memo.Model.Note;

import java.util.List;

public class Viewmodel extends AndroidViewModel {
    NoteRepository noteRepository;
    LiveData<List<Categorie>> listCat;
    LiveData<List<Note>> listNotes=new MutableLiveData<>();
    LiveData<List<Note>> listAllNotes=new MutableLiveData<>();

    public Viewmodel(@NonNull Application application) {
        super(application);
        noteRepository=new NoteRepository(application);
        listCat=noteRepository.getCategories();
    }

    public void AddCategorie(Categorie categorie){
        noteRepository.addCategorie(categorie);
    }
    public void AddNote(Note note){
        noteRepository.addNote(note);
    }

    public LiveData<List<Categorie>> getCategories(){
        return listCat;
    }

    public void sendNotes(long id){
        LiveData<List<Note>> dataNotes=noteRepository.getNotes(id);
        listNotes=(dataNotes);
    }
    public LiveData<List<Note>> getNotes(){
        return listNotes;
    }

    public void sendAllNotes(){
        LiveData<List<Note>> dataAllNotes=noteRepository.getAllNotes();
        listAllNotes=dataAllNotes;
    }
    public LiveData<List<Note>> getAllNotes(){
        return listAllNotes;
    }

    public void update(String newTitle,String newText,long idNt,long idCat){
        noteRepository.update(newTitle,newText,idNt,idCat);
    }
    public void deletee(long idNt,long idCat){
        noteRepository.deletee(idNt,idCat);
    }
    public void deleteCt(long idCat){
        noteRepository.deleteCt(idCat);
    }
    public void deleteNotes(long idCat){noteRepository.deleteNotes(idCat);}
}
