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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class login extends AppCompatActivity {

    EditText username;
    EditText password;
    Button btn;
    TextView signup_button;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseFirestore database = FirebaseFirestore.getInstance();


        username = findViewById(R.id.login_idusername);
        password = findViewById(R.id.login_idpasswordtext);
        btn = findViewById(R.id.login_butonlogin);
        signup_button = findViewById(R.id.login_butonsignup);
        progressBar = findViewById(R.id.progress_bar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task<QuerySnapshot> dataUsers = database.collection("users").get();
                progressBar.setVisibility(View.VISIBLE);
                dataUsers.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QueryDocumentSnapshot target = null;
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if (username.getText().toString().equals(documentSnapshot.getData().get("username"))) {
                                    target = documentSnapshot;
                                    break;
                                }
                            }
                            if (target != null) {
                                Toast.makeText(login.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
                                UserManager.setInstance(target);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(login.this, "User tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        if (UserManager.getInstance() != null) {
            Toast.makeText(login.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return;
        }

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, signup.class));
            }
        });
    }
}