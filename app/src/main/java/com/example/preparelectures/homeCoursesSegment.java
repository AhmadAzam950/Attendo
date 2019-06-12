package com.example.preparelectures;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class homeCoursesSegment extends Fragment {
    private RecyclerView recycle_view;
    private coursesAdaptor adaptor;
    private RecyclerView.LayoutManager layout;
    private FirebaseFirestore db;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private SharedPreferences preferences;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_courses_entry, container, false);
        linkObjects(view);
        setUpRecyclerView(view);

        // attachListener(view);
        return view;
    }

    void setUpRecyclerView(View v) {
        recycle_view = (RecyclerView) v.findViewById(R.id.recycle_view_course);
        recycle_view.setHasFixedSize(true);
        layout = new LinearLayoutManager(getActivity());
        recycle_view.setLayoutManager(layout);
        Query q = db.collection("teachers").document(firebaseAuth.getUid()).collection("classes").orderBy("courseId");
        FirestoreRecyclerOptions<courses> options = new FirestoreRecyclerOptions.Builder<courses>()
                .setQuery(q, courses.class)
                .build();
        adaptor = new coursesAdaptor(options);
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
                Intent intent=new Intent(getActivity(), lecturesActivity.class);

                intent.putExtra("classId",adaptor.getItemValue(viewHolder.getAdapterPosition()));
                startActivity(intent);
            }
        }).attachToRecyclerView(recycle_view);
        recycle_view.setVisibility(View.INVISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                recycle_view.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }

    @Override
    public void onStart() {
        super.onStart();
        adaptor.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adaptor.stopListening();
    }

    void linkObjects(View view) {
        db = FirebaseFirestore.getInstance();
        progressBar = view.findViewById(R.id.progress_bar);
    }

    /*public void attachListener(View view) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ManualEntry.class));
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Scanner.class));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Main2Activity.class));
            }
        });
    }*/

}
