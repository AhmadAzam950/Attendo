package com.example.preparelectures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ManualEntry extends AppCompatActivity {

    public boolean[] check = {false, false, false, false, false, false, false, false, false, false, false, false};
    public String[] names = {"Ahmad", "Usman", "Hussain", "Mukarram", "Ehsan", "Ali", "Ahmad", "Usman", "Hussain", "Mukarram", "Ehsan", "Ali"};
    public String[] rollNo = {"Bsef16a029", "Bsef16a041", "Bsef16a009", "Bsef16a045", "Bsef16a036", "Bsef16a023", "Bsef16a029", "Bsef16a041", "Bsef16a009", "Bsef16a045", "Bsef16a036", "Bsef16a023"};
    public boolean flag = false;
    private RecyclerView recycle_view;
    private Adaptor adaptor;
    private RecyclerView.LayoutManager layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);
        recycle_view=(RecyclerView)findViewById(R.id.recycle_view);
        recycle_view.setHasFixedSize(true);
        layout=new LinearLayoutManager(this);
        recycle_view.setLayoutManager(layout);
        adaptor=new Adaptor(names,rollNo,check);
        recycle_view.setAdapter(adaptor);
        Button btn=findViewById(R.id.select);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag)
                {
                    flag=false;
                    for(int i=0;i<check.length;i++)

                    {
                        check[i]=flag;
                    }
                }
                else
                {
                    flag=true;
                    for(int i=0;i<check.length;i++)

                    {
                        check[i]=flag;
                    }
                }
                adaptor.notifyDataSetChanged();
            }
        });

        }

}
