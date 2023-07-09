package com.example.rehabnow.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rehabnow.R;
import com.example.rehabnow.data.User;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.viewHolder> {

    ArrayList<User> doctors;
    OnItemClick onItemClick;

    public PersonAdapter(List<User> list, OnItemClick onClickListener) {
        doctors = (ArrayList<User>) list;
        onItemClick = onClickListener;

    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView ivDoctor;
        LinearLayout mainLayout;
        TextView tvName, tvSpeciality, tvQualification;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            ivDoctor = itemView.findViewById(R.id.ivDoctor);
            tvName = itemView.findViewById(R.id.tvName);
            tvSpeciality = itemView.findViewById(R.id.tvSpeciality);
            tvQualification = itemView.findViewById(R.id.tvQualification);

        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_doctors, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvName.setText(doctors.get(position).getFullName());

        holder.tvSpeciality.setText(doctors.get(position).getSpeciality());
        holder.tvQualification.setText(doctors.get(position).getQualification());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(doctors.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }
}

