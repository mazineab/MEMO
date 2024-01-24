package com.example.memo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.memo.R;
import com.example.memo.View.Fragment.CategorieFragment;
import com.example.memo.View.Fragment.NoteFragment;
import com.example.memo.View.Fragment.ShowFragment;

public class PageNote extends AppCompatActivity {
    String title="";
    String body="";
    long idCt=0;
    long idNt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_note);
        Intent intent=getIntent();
        String type=intent.getStringExtra("type");
        if(type.equals("show")){
            title=intent.getStringExtra("title");
            body=intent.getStringExtra("body");
            idCt=intent.getLongExtra("idCt",0);
            idNt=intent.getLongExtra("idNt",0);
        }
        update(type);
    }
    public void update(String str){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if(str.equals("Categorie")){
            fragmentTransaction.replace(R.id.frame,new CategorieFragment());
        }
        else if(str.equals("Note")){
            fragmentTransaction.replace(R.id.frame,new NoteFragment());
        }
        else{
            ShowFragment showFragment = new ShowFragment();

            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("body", body);
            bundle.putLong("idCt",idCt);
            bundle.putLong("idNt",idNt);
            showFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.frame,showFragment);
        }
        fragmentTransaction.commit();
    }
}















