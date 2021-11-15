package com.example.projectportofolio.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectportofolio.EditProfile;
import com.example.projectportofolio.MainActivity;
import com.example.projectportofolio.R;
import com.example.projectportofolio.auth.UserManager;
import com.example.projectportofolio.auth.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.concurrent.Executor;

public class ProfileFragment extends Fragment {

    TextView username_txt, email_txt, phone_txt, profile_name;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    RelativeLayout progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        profile_name = view.findViewById(R.id.profile_username);
        username_txt = view.findViewById(R.id.username_txt);
        email_txt = view.findViewById(R.id.email_txt);
        phone_txt = view.findViewById(R.id.phone_txt);
        progressBar = view.findViewById(R.id.load_profile);

        Task<QuerySnapshot> dataUsers = mStore.collection("users").get();
        dataUsers.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    username_txt.setText(UserManager.getInstance().getString("username"));
                    email_txt.setText(UserManager.getInstance().getString("email"));
                    phone_txt.setText(UserManager.getInstance().getString("phone"));
                    profile_name.setText(UserManager.getInstance().getString("username"));
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_edit = view.findViewById(R.id.edit_profile_btn);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                intent.putExtra("username",UserManager.getInstance().getString("username"));
                intent.putExtra("email",UserManager.getInstance().getString("email"));
                intent.putExtra("pass",UserManager.getInstance().getString("password"));
                intent.putExtra("phone",UserManager.getInstance().getString("phone"));
                startActivity(intent);
            }
        });

        Button btn_logout = view.findViewById(R.id.logout_btn);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Log out");
                builder.setMessage("Ingin Keluar ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        DocumentSnapshot target = null;
                        UserManager.setInstance(target);
                        Intent intent = new Intent(getActivity(), login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
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
