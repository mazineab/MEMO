package com.example.memo.View.Adapters;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo.R;

public class ViewHolderNote extends RecyclerView.ViewHolder {
//    RecyInterface rcy;
    TextView title,text;
    public ViewHolderNote(@NonNull View itemView) {
        super(itemView);
        title=itemView.findViewById(R.id.titleNote);
        text=itemView.findViewById(R.id.textNote);
    }
}
