package com.example.preparelectures;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;

import javax.annotation.Nullable;

public class TeacherFirstActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_teacher_main);
        linkObjects();
        PreferenceManager.setDefaultValues(this,R.xml.settings,false);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        checkAuthentication();
        checkDocumentChange(user);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, draw, toolbar, R.string.nav_draw_open, R.string.nav_draw_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setupDefaultView(savedInstanceState);

    }
    void checkDocumentChange(FirebaseUser user)
    {
        uid=user.getUid();
        userProfile=db.collection("teachers").document(uid);
        userProfile.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e==null) {
                    if (documentSnapshot.exists()) {
                        TeachersProfile studentProfile = documentSnapshot.toObject(TeachersProfile.class);
                        getSupportActionBar().setTitle("Teacher Home");
                        View v=findViewById(R.id.nav_view);
                        TextView textView=v.findViewById(R.id.titleName);
                        textView.setText(studentProfile.getFirstName());
                        sharedPreferences=getSharedPreferences("MyFile",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        String json=new Gson().toJson(studentProfile);
                        editor.putString("TeachersProfile",json);
                        editor.commit();
                    }
                }
                else
                {

                }

            }
        });
    }

    void linkObjects() {
        navigationView = findViewById(R.id.nav_view);
        draw = findViewById(R.id.draw_layout);
        toolbar = findViewById(R.id.toolbar);
    }
    void setupDefaultView(Bundle savedInstanceState)
    {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                    new TeacherCoursesFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }
    void checkAuthentication()
    {
        if (firebaseAuth.getCurrentUser() == null) {
            Intent I = new Intent(this, Login_Page_Activity.class);
            finish();
            startActivity(I);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                        new TeacherProfileFragment()).commit();
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
                        new TeacherCoursesFragment()).commit();
                break;
            case R.id.logout:
                logout();
                break;
        }
        draw.closeDrawer(GravityCompat.START);
        return true;

    }
    void logout()
    {
        sharedPreferences.edit().remove("TeachersProfile").commit();
        //  sharedPreferences.edit().remove("profile").commit();
        if(sharedPreferences.getString("TeachersProfile","")=="") {
            Toast.makeText(this,"profile",Toast.LENGTH_LONG).show();
            firebaseAuth.signOut();
            Intent I = new Intent(this, Login_Page_Activity.class);
            finish();
            startActivity(I);
        }

    }
    @Override
    public void onBackPressed() {
        if (draw.isDrawerOpen(GravityCompat.START)) {
            draw.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            finishAffinity();
            finish();
        }
    }
}
