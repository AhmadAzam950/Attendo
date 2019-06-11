package com.example.preparelectures;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ManualEntry extends AppCompatActivity {

    public boolean[] check = {false, false, false, false, false, false, false, false, false, false, false, false};
    public String[] names = {"Ahmad", "Usman", "Hussain", "Mukarram", "Ehsan", "Ali", "Ahmad", "Usman", "Hussain", "Mukarram", "Ehsan", "Ali"};
    public String[] rollNo = {"Bsef16a029", "Bsef16a041", "Bsef16a009", "Bsef16a045", "Bsef16a036", "Bsef16a023", "Bsef16a029", "Bsef16a041", "Bsef16a009", "Bsef16a045", "Bsef16a036", "Bsef16a023"};
    public boolean flag = true;
    private RecyclerView recycle_view;
    private Adaptor adaptor;
    private RecyclerView.LayoutManager layout;
    private FirebaseFirestore db;
    private SharedPreferences preferences;
private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);
        db = FirebaseFirestore.getInstance();
        setUpRecyclerView();
        preferences=getSharedPreferences("Yo",MODE_PRIVATE);
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
                //adaptor.notifyDataSetChanged();
            }
        });

    }

    void setUpRecyclerView() {
        recycle_view = (RecyclerView) findViewById(R.id.recycle_view);
        recycle_view.setHasFixedSize(true);
        layout = new LinearLayoutManager(this);
        recycle_view.setLayoutManager(layout);
        Query q = db.collection("students").orderBy("rollNo", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<studentProfile> options = new FirestoreRecyclerOptions.Builder<studentProfile>()
                .setQuery(q, studentProfile.class)
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
