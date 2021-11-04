package com.example.projectportofolio.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectportofolio.MainActivity;
import com.example.projectportofolio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    EditText username;
    EditText email;
    EditText password;
    EditText phone;
    Button btn;
    TextView signin_button;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        username =findViewById(R.id.signup_idusertext);
        email = findViewById(R.id.signup_idemail);
        password = findViewById(R.id.signup_idpassword);
        phone = findViewById(R.id.signup_idphone);
        progressBar = findViewById(R.id.progress_bar);
        btn = findViewById(R.id.signup_buttonregister);
        signin_button = findViewById(R.id.signin_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (UserManager.getInstance() != null) {
                    Toast.makeText(signup.this, "Berhasil Masuk!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signup.this, MainActivity.class));
                    finish();
                    return;
                }

                if (username.getText() == null || username.getText().toString().isEmpty()) {
                    Toast.makeText(signup.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.getText() == null || email.getText().toString().isEmpty()) {
                    Toast.makeText(signup.this, "Email cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText() == null || password.getText().toString().isEmpty()) {
                    Toast.makeText(signup.this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.getText() == null || phone.getText().toString().isEmpty()) {
                    Toast.makeText(signup.this, "Phone cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Task<QuerySnapshot> dataUsers = database.collection("users").get();

                dataUsers.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot target = null;

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if (username.getText().toString().equals(documentSnapshot.getData().get("username"))) {
                                    target = documentSnapshot;
                                    break;
                                }
                            }
//
//                            if (target != null) {
//                                Toast.makeText(signup.this, "User already exist!", Toast.LENGTH_SHORT).show();
//                                return;
//                            }

                            Map<String, Object> map = new HashMap<>();
                            map.put("email", email.getText().toString());
                            map.put("username", username.getText().toString());
                            map.put("password", password.getText().toString());
                            map.put("phone", phone.getText().toString());
                            Task<DocumentReference> documentReferenceTask = database.collection("users").add(map);

                            documentReferenceTask.addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        task.getResult().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    UserManager.setInstance(task.getResult());
                                                    Toast.makeText(signup.this, "User Created", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(signup.this, login.class);
                                                    startActivity(intent);
                                                    closeContextMenu();
                                                } else  {
                                                    Toast.makeText(signup.this, "User Created but failed to fetch user from database", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else  {
                                        Toast.makeText(signup.this, "Failed to Create User", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this, login.class));
            }
        });

    }
}