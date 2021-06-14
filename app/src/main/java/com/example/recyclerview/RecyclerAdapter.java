package com.example.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public static final String TAG = "RecyclerAdapter";
    int count = 0;
    ArrayList<String> movieList;
    RecyclerViewClickInterface recyclerViewClickInterface;

    public RecyclerAdapter(ArrayList<String> moviesList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.movieList = moviesList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder" + count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textview.setText(movieList.get(position));
        holder.rowCountTextView.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textview, rowCountTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textview = itemView.findViewById(R.id.textView);
            rowCountTextView = itemView.findViewById(R.id.rowCountTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getBindingAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    recyclerViewClickInterface.onLongClick(getBindingAdapterPosition());
                    return true;
                }
            });

        }


    }
}
