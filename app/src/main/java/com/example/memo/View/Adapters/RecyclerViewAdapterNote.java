package com.example.memo.View.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo.Model.DatabaseApp;
import com.example.memo.Model.Note;
import com.example.memo.View.PageNote;
import com.example.memo.R;
import com.example.memo.View.MainActivity;
import com.example.memo.ViewModel.Viewmodel;

import java.util.ArrayList;

public class RecyclerViewAdapterNote extends RecyclerView.Adapter<ViewHolderNote> {

    ArrayList<Note> notes;
    Context context;
    Viewmodel viewmodel;

    public RecyclerViewAdapterNote(ArrayList<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
        viewmodel=new ViewModelProvider((ViewModelStoreOwner) context).get(Viewmodel.class);
    }



    @NonNull
    @Override
    public ViewHolderNote onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderNote(LayoutInflater.from(parent.getContext()).inflate(R.layout.stl_note,null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNote holder, int position) {
        Note nt=notes.get(position);
        holder.title.setText(nt.title);
        holder.text.setText(nt.text);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PageNote.class);
                intent.putExtra("idNt",nt.id);
                intent.putExtra("title",nt.title);
                intent.putExtra("body",nt.text);
                intent.putExtra("idCt",nt.CategoryId);
                intent.putExtra("type","show");
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Request remove note")
                        .setMessage("you want to remove this note!?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                viewmodel.deletee(nt.id,nt.CategoryId);
                                notes.remove(position);
                                for(Note ntt: MainActivity.AllNotes){
                                    if(ntt.id==nt.id){
                                        MainActivity.AllNotes.remove(ntt);
                                    }
                                }

                                MainActivity.updateAdN(RecyclerViewAdapterNote.this);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


}
