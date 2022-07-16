package com.example.mentalstatus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText mFullName, mEmail, mPassword, mConfirmPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    String currentDate;
    String userID;
    Calendar calendar;
    FirebaseFirestore fstore;

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private int availableUsers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.cfpassword);
        mRegisterBtn = findViewById(R.id.regsiterBtn);
        mLoginBtn = findViewById(R.id.createText);

        fstore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("users")){
                    availableUsers = (int) snapshot.child("users").getChildrenCount();
                }
                else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = mFullName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String cfpassword = mConfirmPassword.getText().toString().trim();
                calendar = Calendar.getInstance();
                currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

                if(TextUtils.isEmpty(fname)){
                    mFullName.setError("Please input your name.");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(TextUtils.isEmpty(cfpassword)){
                    mConfirmPassword.setError("Password Confirmation is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Oops, Password must be 6 Characters or longer.");
                    return;
                }

                if(password.equals(cfpassword)){
                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
//                                databaseReference.child("Users").child(String.valueOf(availableUsers + 1)).child("Full Name").setValue(fname);
//                                databaseReference.child("Users").child(String.valueOf(availableUsers + 1)).child("Email").setValue(email);
//                                databaseReference.child("Users").child(String.valueOf(availableUsers + 1)).child("Date Created").setValue(currentDate);
                                userID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fstore.collection("users").document(userID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("fullName", fname);
                                user.put("email", email);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSucess: User Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: "+ e.toString());
                                    }
                                });
                                Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                            else {
                                Toast.makeText(Register.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }

                else {
                    mConfirmPassword.setError("Password doesn't match");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Register the user in firebase


            }
        });


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Login.class));
    }
}