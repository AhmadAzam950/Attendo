package com.example.preparelectures;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;

import javax.annotation.Nullable;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout draw;
    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private DocumentReference userProfile;
    private String uid;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        linkObjects();
        PreferenceManager.setDefaultValues(this,R.xml.settings,false);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if (firebaseAuth.getCurrentUser() == null) {
            Intent I = new Intent(this, Login_Page.class);
            finish();
            startActivity(I);
        }
        uid=user.getUid();
        userProfile=db.collection("students").document(uid);
        userProfile.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e==null) {
                    if (documentSnapshot.exists()) {
                        studentProfile studentProfile = documentSnapshot.toObject(studentProfile.class);
                        getSupportActionBar().setTitle(studentProfile.getRollNo());
                        sharedPreferences=getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        String json=new Gson().toJson(studentProfile);
                        editor.putString("profile",json);
                        editor.commit();
                    }
                }
                else
                {

                }

            }
        });
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, draw, toolbar, R.string.nav_draw_open, R.string.nav_draw_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                    new homeSegment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }

    }
    void linkObjects() {
        navigationView = findViewById(R.id.nav_view);
        draw = findViewById(R.id.draw_layout);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                        new profieSegment()).commit();
                break;
            case R.id.lecture:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                        new lectureSegment()).commit();
                break;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                        new preference()).commit();
                break;
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                        new homeSegment()).commit();
                break;
            case R.id.logout:
                firebaseAuth.signOut();
                sharedPreferences.edit().remove("teacherProfile");
                Intent I = new Intent(this, Login_Page.class);
                finish();
                startActivity(I);
                break;
        }
        draw.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        if (draw.isDrawerOpen(GravityCompat.START)) {
            draw.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
