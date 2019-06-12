package com.example.preparelectures;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;

public class homeActivity extends AppCompatActivity {
    private Button btn;
    private Button btn1;
    FirebaseFirestore db;
    private Button btn3;
    private String classId;
    private String lectureId;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_segment);
        db=FirebaseFirestore.getInstance();
        Intent intent=getIntent();
        lectureId=intent.getStringExtra("lectureId");
        classId=intent.getStringExtra("classId");
        getSupportActionBar().setTitle(classId+lectureId);
        linkObjects();
        attachListener();
    }
    void linkObjects() {
        btn = (Button) findViewById(R.id.btn);
        btn1 = findViewById(R.id.bt1n);
        btn3 = findViewById(R.id.btn3);
        floatingActionButton=findViewById(R.id.submitButton);
    }

    public void attachListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), ManualEntry.class);
                intent.putExtra("lectureId",lectureId);
                intent.putExtra("classId",classId);
                startActivity(intent);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Scanner.class));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("lectureId",lectureId);
                intent.putExtra("classId",classId);
                startActivity(intent);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<studentProfile> list = new ArrayList<studentProfile>();
                CollectionReference studentCollection = db.collection("tempdata").document(lectureId+classId).
                        collection("studentsstate");
                studentCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            studentProfile studentProfile = documentSnapshot.toObject(studentProfile.class);
                            list.add(studentProfile);
                        }
                        DocumentReference ref;
                        WriteBatch batch = db.batch();
                        for (studentProfile studentProfile : list) {
                            ref = db.collection("classes").document(classId).
                                    collection("lectures").document(lectureId).collection("attendences").
                            document(studentProfile.getUid());
                            attendence attendence=new attendence();
                            attendence.setPresence(studentProfile.isCheck());
                            batch.set(ref, attendence);
                        }
                        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    db.collection("classes").document(classId).collection("lectures")
                                            .document(lectureId).update("marked",true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                finish();
                                                startActivity(new Intent(getApplicationContext(),TeacherMain.class));
                                            }
                                        }
                                    });
                                    //startActivity(intent);
                                }
                            }
                        });
                    }
                });
            }
        });
    }

}
