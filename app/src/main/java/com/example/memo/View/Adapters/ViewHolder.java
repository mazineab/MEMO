package com.example.memo.View.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView txtCat;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        txtCat=itemView.findViewById(R.id.txtCat);
    }
}
