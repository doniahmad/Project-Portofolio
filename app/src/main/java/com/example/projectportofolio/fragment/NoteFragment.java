package com.example.projectportofolio.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectportofolio.Note.NoteEdit;
import com.example.projectportofolio.R;
import com.example.projectportofolio.RealmHelper;
import com.example.projectportofolio.adapter.NoteAdapter;
import com.example.projectportofolio.model.NoteModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static io.realm.Realm.getApplicationContext;

public class NoteFragment extends Fragment {
    FloatingActionButton btn_tambah;
    Realm realm;
    RealmHelper realmHelper;
    RecyclerView recyclerView;
    NoteAdapter adapter;
    List<NoteModel> list;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_tambah= view.findViewById(R.id.tambah_button);
        progressBar = view.findViewById(R.id.progress_bar);
        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NoteEdit.class);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.note_rv);

        realm.init(getActivity());
        realm = Realm.getDefaultInstance();

        realmHelper = new RealmHelper(realm);
        list = new ArrayList<>();

        list = realmHelper.getAllDatabase();
        adapter = new NoteAdapter(getActivity(), list,realmHelper);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
        adapter = new NoteAdapter(getActivity(),list,realmHelper);
        recyclerView.setAdapter(adapter);
    }
}
