package com.example.expet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class patientReg extends AppCompatActivity {
    private TextView regPageQuestion;
    private EditText petName, petType, petAge, petGender, petColour;
    private Button regButton;
    ImageView signup_back_button;

    String name, type, gender, colour, age;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;
    private ProgressDialog loader;

    FirebaseFirestore firestore;

    TextView txtSignIn;
    EditText edtFullName, edtEmail, edtMobile, edtPassword, edtConfirmPassword;
    ProgressBar progressBar;
    Button btnSignUp;
    String txtFullName, txtEmail, txtMobile, txtPassword, txtConfirmPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
  //  private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg);

        regPageQuestion = findViewById(R.id.regPageQuestion);
        regPageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(patientReg.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signup_back_button = findViewById(R.id.signup_back_button);
        signup_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(patientReg.this, SelectRegistration.class);
                startActivity(i);
            }
        });


        petName = findViewById(R.id.petName);
        petType = findViewById(R.id.petType);
        petAge = findViewById(R.id.petAge);
        petGender = findViewById(R.id.petGender);
        petColour = findViewById(R.id.petColour);
        regButton = findViewById(R.id.regButton);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();


        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = petName.getText().toString().trim();
                final String type = petType.getText().toString().trim();
                final String age = petAge.getText().toString().trim();
                final String gender = petGender.getText().toString().trim();
                final String colour = petColour.getText().toString().trim();

                if (!TextUtils.isEmpty(name)) {
                    if (!TextUtils.isEmpty(type)) {
                        if (!TextUtils.isEmpty(age)) {
                            if (!TextUtils.isEmpty(gender)) {
                                if (!TextUtils.isEmpty(colour)) {

                                    SignUpUser();
                                } else {
                                    petName.setError("Name is required!");
                                }
                            } else {
                                petType.setError("Type is required!");
                            }
                        } else {
                            petAge.setError("Age is required!");
                        }
                    } else {
                        petGender.setError("Gender is required!");
                    }
                } else {
                        petColour.setError("Colour is required!");
                    }
                }
        });
       /* if (TextUtils.isEmpty(name)) {
            petName.setError("Name is required!");
            return;
        }
        if (TextUtils.isEmpty(type)) {
            petType.setError("Type is required!");
            return;
        }
        if (TextUtils.isEmpty(age)) {
            petAge.setError("Age is required!");
            return;
        }
        if (TextUtils.isEmpty(gender)) {
            petGender.setError("Gender is required!");
            return;
        }
        if (TextUtils.isEmpty(colour)) {
            petColour.setError("Colour is required!");
            return;
        } else {
            loader.setMessage("Registration in progress...");
            loader.setCanceledOnTouchOutside(false);
            loader.show();
        }
*/
        //  mAuth.createUserWithEmailAndPassword


       // firestore = FirebaseFirestore.getInstance();


    }
        private void SignUpUser() {
            progressBar.setVisibility(View.VISIBLE);
            btnSignUp.setVisibility(View.INVISIBLE);

            mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("FullName", txtFullName);
                    user.put("Email", txtEmail);
                    user.put("MobileNumber", txtMobile);

                    db.collection("users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(patientReg.this, "Data Stored Successfully !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(patientReg.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(patientReg.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(patientReg.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    btnSignUp.setVisibility(View.VISIBLE);
                }
            });

        }
}