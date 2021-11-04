package com.example.projectportofolio.Note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.projectportofolio.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetail extends AppCompatActivity {

    String title,text;
    Integer id;
    Bundle bundle;
    MaterialToolbar topappbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        TextView title_edit,text_edit;
        FloatingActionButton edit_btn;

        title_edit = findViewById(R.id.title_detail);
        text_edit = findViewById(R.id.text_detail);
        edit_btn = findViewById(R.id.edit_button);
        topappbar = findViewById(R.id.topAppBar);
        setSupportActionBar(topappbar);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            id = Integer.parseInt(bundle.getString("id"));
            title = bundle.getString("title");
            text = bundle.getString("desc");

            title_edit.setText(title);
            text_edit.setText(text);
        }

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NoteEdit.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                intent.putExtra("text",text);
                startActivity(intent);
            }
        });

        topappbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}