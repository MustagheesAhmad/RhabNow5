package com.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rehabnow.DoctorInformation;
import com.example.rehabnow.R;

import java.util.ArrayList;

public class AdapterCard extends RecyclerView.Adapter<AdapterCard.ViewHolder> {
    ArrayList<DoctorInformation> Doctor;
    public AdapterCard (Context context, ArrayList<DoctorInformation> list)
    {
        Doctor =list;

    }
    public class  ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivDoctor;
        TextView tvName, tvSpeciality, tvLocation, tvQualification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDoctor=itemView.findViewById(R.id.ivDoctor);
            tvName=itemView.findViewById(R.id.tvName);
            tvSpeciality=itemView.findViewById(R.id.tvSpeciality);
            tvLocation=itemView.findViewById(R.id.tvLocation);
            tvQualification=itemView.findViewById(R.id.tvQualification);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_doctors,
            parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.ivDoctor.setImageResource(R.drawable.doctorimage);
    holder.tvName.setText(Doctor.get(position).getName());
    holder.tvQualification.setText(Doctor.get(position).getEducation());
    holder.tvSpeciality.setText(Doctor.get(position).getSpeciality());
    holder.tvLocation.setText(Doctor.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return Doctor.size();
    }
}
