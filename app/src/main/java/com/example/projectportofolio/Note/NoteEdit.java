package com.example.projectportofolio.Note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectportofolio.MainActivity;
import com.example.projectportofolio.R;
import com.example.projectportofolio.RealmHelper;
import com.example.projectportofolio.fragment.NoteFragment;
import com.example.projectportofolio.model.NoteModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

public class NoteEdit extends AppCompatActivity {

    EditText edit_title, edit_text;
    String title,text;
    Integer id;
    FloatingActionButton btn_yes;
    Realm realm;
    RealmHelper realmHelper;
    NoteModel noteModel;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        edit_title = findViewById(R.id.title_edit);
        edit_text = findViewById(R.id.text_edit);
        btn_yes = findViewById(R.id.yes_button);

        Realm.init(this);
        RealmConfiguration config = Realm.getDefaultConfiguration();
        realm = Realm.getInstance(config);
        realmHelper = new RealmHelper(realm);

        bundle = getIntent().getExtras();
        if (bundle != null) {
             id = bundle.getInt("id");
             title = bundle.getString("title");
             text = bundle.getString("text");

            edit_title.setText(title);
            edit_text.setText(text);

            btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = edit_title.getText().toString();
                    String desc = edit_text.getText().toString();

                    if (!title.equals("") && !desc.equals("")) {
                        realmHelper.update(id, title, desc);
                        Intent intent = new Intent(getApplicationContext(),NoteDetail.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("id",id.toString());
                        intent.putExtra("title",title);
                        intent.putExtra("desc",desc);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Edit data berhasil!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = edit_title.getText().toString();
                    String desc = edit_text.getText().toString();

                    if (!title.equals("") && !desc.equals("")) {
                        noteModel = new NoteModel();
                        noteModel.setTitleNote(title);
                        noteModel.setDescNote(desc);

                        realmHelper = new RealmHelper(realm);
                        realmHelper.save(noteModel);

                        Toast.makeText(getApplicationContext(),"Menambah data berhasil!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }


    }
}