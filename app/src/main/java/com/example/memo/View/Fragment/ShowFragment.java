package com.example.memo.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.memo.Model.DatabaseApp;
import com.example.memo.R;
import com.example.memo.ViewModel.Viewmodel;
import com.google.android.material.snackbar.Snackbar;


public class ShowFragment extends Fragment {
    Button btnUpdt;
    EditText edUpdt,edMltUpdt;
    long idCt;
    long idNt;
    Viewmodel viewmodel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_show, container, false);
        viewmodel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(Viewmodel.class);
        btnUpdt=v.findViewById(R.id.buttonUpdt);
        edUpdt=v.findViewById(R.id.editTextTextUdpt);
        edMltUpdt=v.findViewById(R.id.editTextTextMultiLine3Updt);
        String title = getArguments().getString("title");
        String body = getArguments().getString("body");
        idCt=getArguments().getLong("idCt",0);
        idNt=getArguments().getLong("idNt",0);
        edUpdt.setText(title);edMltUpdt.setText(body);
        edUpdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                btnUpdt.setVisibility(View.VISIBLE);
            }
        });
        edMltUpdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                btnUpdt.setVisibility(View.VISIBLE);
            }
        });

        btnUpdt.setOnClickListener(this::updatee);
        return v;
    }

    private void updatee(View view) {
        String edTittle=edUpdt.getText().toString();
        String edBody=edMltUpdt.getText().toString();
        viewmodel.update(edTittle,edBody,idNt,idCt);

        Snackbar.make(getActivity().getCurrentFocus(),"update successfully",Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().finish();
            }
        },1200);
    }
}