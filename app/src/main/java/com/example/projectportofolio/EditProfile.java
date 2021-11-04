package com.example.projectportofolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectportofolio.auth.UserManager;
import com.example.projectportofolio.auth.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    MaterialToolbar topappbar;
    EditText edit_username,edit_email,edit_pass,edit_phone;
    Button btn_save;
    Bundle bundle;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        topappbar = findViewById(R.id.topAppBar);
        setSupportActionBar(topappbar);
        edit_username = findViewById(R.id.edit_username);
        edit_email = findViewById(R.id.edit_email);
        edit_pass = findViewById(R.id.edit_pass);
        edit_phone = findViewById(R.id.edit_phone);
        btn_save = findViewById(R.id.edit_save_btn);

        firestore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        bundle = getIntent().getExtras();
        if (bundle != null) {
            String username = bundle.getString("username");
            String email = bundle.getString("email");
            String pass = bundle.getString("pass");
            String phone = bundle.getString("phone");

            edit_username.setText(username);
            edit_email.setText(email);
            edit_pass.setText(pass);
            edit_phone.setText(phone);

            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", edit_email.getText().toString());
                    map.put("username", edit_username.getText().toString());
                    map.put("password", edit_pass.getText().toString());
                    map.put("phone", edit_phone.getText().toString());

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Ubah Profile");
                    builder.setMessage("Mengubah profile memerlukan login ulang. Tetap ubah ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DocumentReference reference = firestore.collection("users").document(UserManager.getInstance().getId());
                            reference.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        UserManager.setInstance(null);
                                        Intent intent = new Intent(getApplicationContext(),login.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),"Profile Diperbarui, Mohon Log in lagi!",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Profile Gagal Diperbarui",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        }
    }
}