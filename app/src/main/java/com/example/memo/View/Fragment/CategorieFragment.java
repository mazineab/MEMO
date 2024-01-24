package com.example.memo.View.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.memo.Model.Categorie;
import com.example.memo.Model.DatabaseApp;
import com.example.memo.R;
import com.example.memo.ViewModel.Viewmodel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class CategorieFragment extends Fragment {
    TextInputLayout textInputLayout;
    EditText ed;
    Button btnAdd;
    DatabaseApp databaseApp;
    Viewmodel viewmodel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_categorie, container, false);
        btnAdd=view.findViewById(R.id.btnAddCat);
        textInputLayout=view.findViewById(R.id.textInputLayout);
        Context context=getContext();
        databaseApp=DatabaseApp.getInstance(context);
        ed=textInputLayout.getEditText();
        viewmodel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(Viewmodel.class);
        btnAdd.setOnClickListener(this::AddCategory);
        return view;

    }
    private void AddCategory(View view) {
        boolean trv=false;
        String categorieName=ed.getText().toString();
        Categorie categorie=new Categorie(categorieName);
        if(categorieName.isEmpty()){
            ed.setError("Fill the field");
        }
        else{
            trv=true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    viewmodel.AddCategorie(categorie);
                }
            }).start();

        }
        if(trv){
            Snackbar.make(getActivity().getCurrentFocus(),"Category added successfully", Snackbar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().finish();
                }
            },1200);
        }
    }
}