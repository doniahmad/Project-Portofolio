package com.example.projectportofolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;

public class NewsDetail extends AppCompatActivity {

    MaterialToolbar topappbar;
    String title,author,publish,img,content,url;
    TextView title_news,author_news,publish_news,content_news;
    ImageView image_news;
    Button btn_seemore;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        topappbar = findViewById(R.id.topAppBar);
        setSupportActionBar(topappbar);

        title_news = findViewById(R.id.title_news);
        author_news = findViewById(R.id.author_news);
        publish_news = findViewById(R.id.publish_news);
        content_news = findViewById(R.id.content_news);
        image_news = findViewById(R.id.img_news);
        btn_seemore = findViewById(R.id.see_more_btn);

        bundle = getIntent().getExtras();
        if (bundle != null){
         title = bundle.getString("title");
         author = bundle.getString("author");
         publish = bundle.getString("publish");
         img = bundle.getString("img");
         content = bundle.getString("content");
         url = bundle.getString("url");

         title_news.setText(title);
         author_news.setText(author);
         publish_news.setText(publish);
         content_news.setText(content);

         Picasso.get().load(img).into(image_news);

         btn_seemore.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getApplicationContext(),WebsiteView.class);
                 intent.putExtra("url",url);
                 startActivity(intent);
             }
         });
        }

        topappbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}