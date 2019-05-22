package com.example.preparelectures;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    protected DrawerLayout draw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolba=findViewById(R.id.toolbar);
        setSupportActionBar(toolba);
         draw=findViewById(R.id.draw_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,draw,toolba,R.string.nav_draw_open,R.string.nav_draw_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();
        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),ManualEntry.class));
            }
        });
        Button btn1=findViewById(R.id.bt1n);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Scanner.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(draw.isDrawerOpen(GravityCompat.START))
        {
            draw.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}
