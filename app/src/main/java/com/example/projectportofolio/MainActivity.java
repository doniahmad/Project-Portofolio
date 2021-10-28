package com.example.projectportofolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.projectportofolio.fragment.ListFragment;
import com.example.projectportofolio.fragment.NoteFragment;
import com.example.projectportofolio.fragment.ProfileFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = findViewById(R.id.appbar);
        appbar.setTitle("News");


        BottomNavigationView bottomNav = findViewById(R.id.bot_nav);
        bottomNav.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ListFragment()).commit();
    }

    private NavigationBarView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_list:
                    selectedFragment = new ListFragment();
                    appbar.setTitle("News");
                    break;

                case R.id.nav_note:
                    selectedFragment = new NoteFragment();
                    appbar.setTitle("Note");
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    appbar.setTitle("Profile");
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };
}