package com.example.preparelectures;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;

public class lecturesActivity extends AppCompatActivity {

    public boolean flag = true;
    private RecyclerView recycle_view;
    private lecturesAdaptor adaptor;
    private RecyclerView.LayoutManager layout;
    private FirebaseFirestore db;
    private SharedPreferences preferences;
    private Button btn;
    private String classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures_entry);
        Intent intent = getIntent();
        classId = intent.getStringExtra("classId");
        //getActionBar().setTitle(courseId);
        getSupportActionBar().setTitle(classId);
        db = FirebaseFirestore.getInstance();
        setUpRecyclerView();

    }

    void setUpRecyclerView() {
        recycle_view = (RecyclerView) findViewById(R.id.recycle_view_lectures);
        recycle_view.setHasFixedSize(true);
        layout = new LinearLayoutManager(this);
        recycle_view.setLayoutManager(layout);
        Query q = db.collection("classes").document(classId).collection("lectures")
                .whereEqualTo("marked", false);
        FirestoreRecyclerOptions<lectures> options = new FirestoreRecyclerOptions.Builder<lectures>()
                .setQuery(q, lectures.class)
                .build();
        adaptor = new lecturesAdaptor(options);
        recycle_view.setAdapter(adaptor);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                /*FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction().
                        replace(R.id.frameLayout,new homeSegment());
                fragmentTransaction.commit();*/
                final Intent intent = new Intent(getApplicationContext(), homeActivity.class);
                final String lectureId = adaptor.getItemValue(viewHolder.getAdapterPosition());
                intent.putExtra("lectureId", adaptor.getItemValue(viewHolder.getAdapterPosition()));
                intent.putExtra("classId", classId);
                final ArrayList<studentProfile> list = new ArrayList<studentProfile>();
                CollectionReference studentCollection = db.collection("classes").document(classId).
                        collection("students");
                studentCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            studentProfile studentProfile = documentSnapshot.toObject(studentProfile.class);
                            studentProfile.setUid(documentSnapshot.getId());
                            list.add(studentProfile);
                        }
                        DocumentReference ref;
                        WriteBatch batch = db.batch();
                        for (studentProfile studentProfile : list) {
                            ref = db.collection("tempdata").document(lectureId + classId).
                                    collection("studentsstate").document(studentProfile.getUid());
                            batch.set(ref, studentProfile);
                        }
                        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });

            }
        }).attachToRecyclerView(recycle_view);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptor.startListening();
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
