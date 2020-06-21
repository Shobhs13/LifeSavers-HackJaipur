package com.example.lifesaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

    private ArrayList<Patient> mPatientList;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView age;
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.patient_name);
            age = (TextView) itemView.findViewById(R.id.patient_age);
            image = (ImageView) itemView.findViewById(R.id.patient_image);
        }

    }


    public PatientAdapter(ArrayList<Patient> patientsList){
        mPatientList = patientsList;
    }
    @NonNull
    @Override
    public PatientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.patient_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull PatientAdapter.ViewHolder holder, int position) {

        Patient patient = mPatientList.get(position);

        TextView nameTextView = holder.name;
        nameTextView.setText(patient.getName());

        TextView ageTextView = holder.age;
        ageTextView.setText(patient.getAge());

        ImageView imageView = holder.image;
        imageView.setImageResource(R.drawable.ic_baseline_person_add_24);



    }

    @Override
    public int getItemCount() {
        return mPatientList.size();
    }
}
