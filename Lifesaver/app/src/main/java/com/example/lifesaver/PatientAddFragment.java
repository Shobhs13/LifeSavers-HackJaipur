package com.example.lifesaver;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientAddFragment extends Fragment {

    EditText name, age,address, number;

    Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_patient, container, false);

        name = view.findViewById(R.id.patient_add_name);
        age = view.findViewById(R.id.patient_add_age);
        number = view.findViewById(R.id.patient_add_phone);
        address = view.findViewById(R.id.patient_add_address);

        button = view.findViewById(R.id.patient_add_save_button);

        PatientAddFragment addPatientFragment = (PatientAddFragment) getFragmentManager().findFragmentByTag("MY_FRAGMENT");
        if (addPatientFragment != null && addPatientFragment.isVisible()) {


            addPatientFragment.getView().setFocusableInTouchMode(true);
            addPatientFragment.getView().requestFocus();
            addPatientFragment.getView().setOnKeyListener( new View.OnKeyListener()
            {
                @Override
                public boolean onKey( View v, int keyCode, KeyEvent event )
                {
                    if( keyCode == KeyEvent.KEYCODE_BACK )
                    {
                        return true;
                    }
                    return false;
                }
            } );
        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Patient patient = new Patient(name.getText().toString(), address.getText().toString(), Integer.valueOf(age.getText().toString()), Long.valueOf(number.getText().toString()));


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Patients/" + FirebaseAuth.getInstance().getUid() + "/");
                ref.push().setValue(patient);
            }
        });



        return view;
    }
}
