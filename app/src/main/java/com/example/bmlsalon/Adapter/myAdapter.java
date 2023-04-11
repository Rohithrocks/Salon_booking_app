package com.example.bmlsalon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bmlsalon.Model.datamodel;
import com.example.bmlsalon.R;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myviewholder>{

    ArrayList<datamodel> dataholder;

    public myAdapter(ArrayList<datamodel> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_review,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.image.setImageResource(dataholder.get(position).getImage());
        holder.userId.setText(dataholder.get(position).getUserId());
        holder.userdesc.setText(dataholder.get(position).getUserdesc());

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder  extends RecyclerView.ViewHolder{
        ImageView image;
        TextView userId,userdesc;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.user_image_view);
            userId = itemView.findViewById(R.id.user_name_text_view);
            userdesc = itemView.findViewById(R.id.user_review_text_view);
        }
    }
}
