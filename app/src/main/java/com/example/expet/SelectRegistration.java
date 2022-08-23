package com.example.expet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectRegistration extends AppCompatActivity {

    private TextView back;
 Button petReg,DoctorReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_registration);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SelectRegistration.this, MainActivity.class);
                startActivity(intent);
            }
        });

        petReg = findViewById(R.id.petReg);
        petReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SelectRegistration.this, patientReg.class);
                startActivity(i);
            }
        });

        DoctorReg = findViewById(R.id.doctorReg);
        DoctorReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SelectRegistration.this, DoctorRegistration.class);
                startActivity(i);
            }
        });

    }
}