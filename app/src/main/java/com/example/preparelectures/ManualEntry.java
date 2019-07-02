package com.example.preparelectures;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ManualEntry extends AppCompatActivity {

    public boolean flag = true;
    private RecyclerView recycle_view;
    private Adaptor adaptor;
    private RecyclerView.LayoutManager layout;
    private FirebaseFirestore db;
    private SharedPreferences preferences;
    private String lectureId;
    private String classId;
private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);

        Intent intent=getIntent();
        lectureId=intent.getStringExtra("lectureId");
        classId=intent.getStringExtra("classId");
        db = FirebaseFirestore.getInstance();
        setUpRecyclerView();
        preferences=getSharedPreferences("MyFile",MODE_PRIVATE);
        btn = findViewById(R.id.select);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    flag = false;
                    preferences.edit().putBoolean("flag",flag).commit();
                    adaptor.updateItemTrue();
                    btn.setText("UnSelect All");
                } else {
                    flag = true;
                    preferences.edit().putBoolean("flag",flag).commit();
                    adaptor.updateItemFalse();
                    btn.setText("Select All");

                }
            }
        });

    }

    void setUpRecyclerView() {
        recycle_view = (RecyclerView) findViewById(R.id.recycle_view);
        recycle_view.setHasFixedSize(true);
        layout = new LinearLayoutManager(this);
        recycle_view.setLayoutManager(layout);
        Query q = db.collection("tempdata").document(lectureId+classId)
                .collection("studentsstate").orderBy("rollNo", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<StudentsProfile> options = new FirestoreRecyclerOptions.Builder<StudentsProfile>()
                .setQuery(q, StudentsProfile.class)
                .build();
        adaptor = new Adaptor(options);
        recycle_view.setAdapter(adaptor);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptor.startListening();
        flag=preferences.getBoolean("flag",true);
        if (flag) {
            btn.setText("Select All");
        } else{
            btn.setText("UnSelect All");

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptor.stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
