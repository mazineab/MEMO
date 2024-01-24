package com.example.memo.View.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.memo.Model.Categorie;
import com.example.memo.Model.DatabaseApp;
import com.example.memo.Model.Note;
import com.example.memo.R;
import com.example.memo.ViewModel.Viewmodel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {
    Spinner sp;
    Button btnAddNote;
    TextInputLayout textInputLayout;
    DatabaseApp databaseApp;
    EditText ed,edMlt;
    List<Categorie> listCat;
    ArrayList<String> listAr;
    Viewmodel viewmodel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_note, container, false);
        viewmodel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(Viewmodel.class);
        btnAddNote=view.findViewById(R.id.btnAddNote);
        sp=view.findViewById(R.id.spinner);
        textInputLayout=view.findViewById(R.id.textInputLayout2);
        ed=textInputLayout.getEditText();
        edMlt=view.findViewById(R.id.editTextTextMultiLine);
        Context context=getContext();
        databaseApp=DatabaseApp.getInstance(context);
        listAr=new ArrayList<>();



        viewmodel.getCategories().observe(getActivity(), new Observer<List<Categorie>>() {
            @Override
            public void onChanged(List<Categorie> categories) {
                listCat=categories;
                for(Categorie ct:listCat){
                    if(ct.name.equals("All Notes")){
                        continue;
                    }
                    else{
                        listAr.add(ct.name);
                    }

                }
                ArrayAdapter<String> ad=new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1,listAr);
                sp.setAdapter(ad);
            }
        });

        btnAddNote.setOnClickListener(this::AddNote);
        return view;
    }

    private void AddNote(View view) {
        boolean trv=false;
        String NoteName=ed.getText().toString();
        String NoteBody=edMlt.getText().toString();
        long id=0;
        String categorieName=sp.getSelectedItem().toString();
        for(Categorie ct:listCat){
            if(ct.name.equals(categorieName)){
                id= ct.id;
                break;
            }
        }
        if(NoteName.isEmpty() || NoteBody.isEmpty() || categorieName.isEmpty() || id==0){
            if(NoteName.isEmpty()){ed.setError("Fill the field");}
            else if (NoteBody.isEmpty()) {edMlt.setError("Fill the field");}
            else if (categorieName.isEmpty() ) {Toast.makeText(getActivity(),"Fill the field",Toast.LENGTH_LONG).show();}
        }
        else{
            trv=true;
            Note note=new Note(NoteName,NoteBody,id);
            viewmodel.AddNote(note);
        }
        if(trv){
            Snackbar.make(getActivity().getCurrentFocus(),"Note added successfully", Snackbar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().finish();
                }
            },1200);
        }


    }
}