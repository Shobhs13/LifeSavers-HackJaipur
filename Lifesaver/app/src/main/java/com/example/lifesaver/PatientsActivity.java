package com.example.lifesaver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatientsActivity extends AppCompatActivity {

    RecyclerView patientReccyclerView;
    ArrayList<Patient> patients;

    FrameLayout frameLayout;
    FloatingActionButton fab;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_patient);

        fab = findViewById(R.id.fab_patient);
        frameLayout = findViewById(R.id.patient_frame);
        patientReccyclerView = findViewById(R.id.patient_rv);

        patients = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Patients/" + FirebaseAuth.getInstance().getUid() + "/");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.i("TAG", "onChildAdded: " + snapshot.getValue());
                patient = snapshot.getValue(Patient.class);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //

        PatientAdapter adapter = new PatientAdapter(patients);
        patientReccyclerView.setAdapter(adapter);

        patientReccyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                frameLayout.setVisibility(View.VISIBLE);

                Fragment addPatientFragment = new PatientAddFragment();


                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.patient_frame, addPatientFragment, "MY_FRAGMENT");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }
}