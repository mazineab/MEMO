package com.example.memo.View.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo.Model.Categorie;
import com.example.memo.Model.DatabaseApp;
import com.example.memo.R;
import com.example.memo.View.MainActivity;
import com.example.memo.ViewModel.Viewmodel;

import java.util.ArrayList;

public class RecyclerViewAdapterCat extends RecyclerView.Adapter<ViewHolder> {
    ArrayList<Categorie> Categories;
    CatClick clickListener;
    DatabaseApp databaseApp;

    Viewmodel viewmodel;
    public void onCatClick(CatClick listner){
        clickListener=listner;
    }

    public RecyclerViewAdapterCat(ArrayList<Categorie> categories,Context context) {
        this.Categories = categories;
        viewmodel=new ViewModelProvider((ViewModelStoreOwner) context).get(Viewmodel.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.stl_categorie,null,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categorie cat=Categories.get(position);
        holder.txtCat.setText(cat.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.click(position);
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Request remove Categorie")
                        .setMessage("you want to remove this Categorie!?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                viewmodel.deleteCt(cat.id);
                                viewmodel.deleteNotes(cat.id);
                                Categories.remove(position);
                                MainActivity.updateAdN(MainActivity.AdN);
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
        return Categories.size();
    }
}
