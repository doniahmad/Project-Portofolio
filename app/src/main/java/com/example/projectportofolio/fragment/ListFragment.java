package com.example.projectportofolio.fragment;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.projectportofolio.MainActivity;
import com.example.projectportofolio.R;
import com.example.projectportofolio.adapter.NewsAdapter;
import com.example.projectportofolio.model.NewsModel;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListFragment extends Fragment implements Toolbar.OnMenuItemClickListener {
    private String BASE_URL = "https://newsapi.org/v2/top-headlines?country=id&apiKey=ff5bb09ab43f48a7b05c9edff080af4b";
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private ArrayList<NewsModel> model;
    private ProgressBar progressBar;
    private MaterialToolbar appbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.list_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.list_rv);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView.setHasFixedSize(true);
        appbar = view.findViewById(R.id.appbar);
        addData();

        appbar.setOnMenuItemClickListener(this);
        appbar.setTitle("Berita Teratas");
    }



    private void addData() {
        AndroidNetworking.get(BASE_URL)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            model = new ArrayList<>();
                            JSONArray newsArray = response.getJSONArray("articles");
                            for (int i = 0; i < newsArray.length(); i++) {
                                JSONObject newsObject = newsArray.getJSONObject(i);
                                String title = newsObject.getString("title");
                                String desc = newsObject.getString("description");
                                String img = newsObject.getString("urlToImage");
                                String url = newsObject.getString("url");
                                String author = newsObject.getString("author");
                                String publish = newsObject.getString("publishedAt");
                                String content = newsObject.getString("content");

                                model.add(new NewsModel(title,desc,img,url,author,publish,content));
                            }
                            adapter = new NewsAdapter(getContext(), model);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Log.d("error", e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", anError.toString());
                    }
                });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        progressBar.setVisibility(View.VISIBLE);
        switch (item.getItemId()){
            case R.id.headline :
                BASE_URL = "https://newsapi.org/v2/top-headlines?country=id&apiKey=ff5bb09ab43f48a7b05c9edff080af4b";
                appbar.setTitle("Berita Teratas");
                addData();
                break;

            case R.id.bisnis :
                BASE_URL = "https://newsapi.org/v2/top-headlines?country=id&category=business&apiKey=ff5bb09ab43f48a7b05c9edff080af4b";
                appbar.setTitle("Berita Bisnis");
                addData();
                break;

            case R.id.kesehatan :
                BASE_URL = "https://newsapi.org/v2/top-headlines?country=id&category=health&apiKey=ff5bb09ab43f48a7b05c9edff080af4b";
                appbar.setTitle("Berita Kesehatan");
                addData();
                break;

            case R.id.olahraga :
                BASE_URL = "https://newsapi.org/v2/top-headlines?country=id&category=sports&apiKey=ff5bb09ab43f48a7b05c9edff080af4b";
                appbar.setTitle("Berita Olahraga");
                addData();
                break;

            case R.id.teknologi :
                BASE_URL = "https://newsapi.org/v2/top-headlines?country=id&category=technology&apiKey=ff5bb09ab43f48a7b05c9edff080af4b";
                appbar.setTitle("Berita Teknologi");
                addData();
                break;

        }
        return false;
    }
}
