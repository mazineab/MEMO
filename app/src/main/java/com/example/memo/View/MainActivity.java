package com.example.memo.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.memo.View.Adapters.CatClick;
import com.example.memo.Model.Categorie;
import com.example.memo.Model.DatabaseApp;
import com.example.memo.Model.Note;
import com.example.memo.R;
import com.example.memo.View.Adapters.RecyclerViewAdapterCat;
import com.example.memo.View.Adapters.RecyclerViewAdapterNote;
import com.example.memo.ViewModel.Viewmodel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    ImageButton btnCat;
    ArrayList<Categorie> cats=new ArrayList<>();
    ArrayList<Note> notesAll=new ArrayList<>();
    public static ArrayList<Note> AllNotes=new ArrayList<>();
    public static RecyclerViewAdapterNote AdN;
    ImageView Add;
    String nomCat="";
    DatabaseApp databaseApp;
    RecyclerViewAdapterCat ad;

    List<Categorie> listCat;
    List<Note> listNote2;
    List<Note> listNote;

    static RecyclerView rcy,rcy2;
    Viewmodel viewmodel;

    long ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcy=findViewById(R.id.rcy);
        rcy2=findViewById(R.id.rcy2);
        Add=findViewById(R.id.Add);
        btnCat=findViewById(R.id.addCat);
        databaseApp=DatabaseApp.getInstance(this);
        viewmodel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(Viewmodel.class);


//      add one category and note by default
        viewmodel.getCategories().observe(this, new Observer<List<Categorie>>() {
            @Override
            public void onChanged(List<Categorie> categories) {
                listCat=categories;
                if(listCat.isEmpty()) {
                    Categorie cat1 = new Categorie("All Notes");
                    Note note = new Note("Default note", "This app is powered by Mazine", 1);
                    viewmodel.AddCategorie(cat1);
                    viewmodel.AddNote(note);
                }
            }
        });


        ad=new RecyclerViewAdapterCat(cats,this);
        LinearLayoutManager lytM=new LinearLayoutManager(this);
        lytM.setOrientation(RecyclerView.HORIZONTAL);
        rcy.setLayoutManager(lytM);
        rcy.setAdapter(ad);

        AdN=new RecyclerViewAdapterNote(AllNotes,MainActivity.this);

        StaggeredGridLayoutManager stg=new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        rcy2.setLayoutManager(stg);
        rcy2.setAdapter(AdN);

        btnCat.setOnClickListener(this::addCategorie);
        Add.setOnClickListener(this::addNote);
    }

    private void addNote(View view) {
        Intent intent=new Intent(MainActivity.this, PageNote.class);
        intent.putExtra("type","Note");
        startActivity(intent);
    }

    private void addCategorie(View view) {
        Intent intent=new Intent(MainActivity.this,PageNote.class);
        intent.putExtra("type","Categorie");
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();

        //show categorys
        viewmodel.getCategories().observe(this, new Observer<List<Categorie>>() {
            @Override
            public void onChanged(List<Categorie> categories) {
                listCat = categories;
                cats.clear();
                cats.addAll(listCat);
                ad.notifyDataSetChanged();
            }
        });

        //show Notes by category
        ad.onCatClick(new CatClick() {
            @Override
            public void click(int pos) {
                AdN.notifyDataSetChanged();
                Categorie ct=cats.get(pos);
                nomCat=ct.name;
                ID=ct.id;
                if(nomCat.equals("All Notes")){
                    AdN=new RecyclerViewAdapterNote(AllNotes,MainActivity.this);
                    updateAdN(AdN);
                }
                else{
                    viewmodel.sendNotes(ID);
                    viewmodel.getNotes().observe(MainActivity.this, new Observer<List<Note>>() {
                        @Override
                        public void onChanged(List<Note> notes) {
                            listNote=notes;
                            notesAll.clear();
                            notesAll.addAll(listNote);
                            AdN=new RecyclerViewAdapterNote(notesAll,MainActivity.this);
                            updateAdN(AdN);
                        }
                    });
                }
            }
        });


        //get notes by id
        viewmodel.sendNotes(ID);
        viewmodel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                listNote=notes;
                notesAll.clear();
                notesAll.addAll(listNote);
                AdN.notifyDataSetChanged();
            }
        });


        //filter categorie default
        viewmodel.sendAllNotes();
        viewmodel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                listNote2=notes;
                AllNotes.clear();
                AllNotes.addAll(listNote2);
                AdN=new RecyclerViewAdapterNote(AllNotes,MainActivity.this);
                updateAdN(AdN);
            }
        });
    }

    public static void updateAdN(RecyclerViewAdapterNote adN) {
        StaggeredGridLayoutManager stg=new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        rcy2.setLayoutManager(stg);
        rcy2.setAdapter(adN);
    }
}