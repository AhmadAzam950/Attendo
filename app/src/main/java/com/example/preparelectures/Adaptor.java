package com.example.preparelectures;

import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class Adaptor extends RecyclerView.Adapter<Adaptor.MyViewHolder> {
    String[] names;
    String[] roll;
    boolean[] check;

    public Adaptor(String[] names, String[] rolls, boolean[] check) {
        this.names = names;
        this.check = check;
        this.roll = rolls;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View txt = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customlayout, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(txt);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.txt.setText(names[i]);
        viewHolder.txt2.setText(roll[i]);
        viewHolder.checkBox.setChecked(check[i]);

    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        TextView txt2;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt = (TextView) itemView.findViewById(R.id.name);
            txt2 = (TextView) itemView.findViewById(R.id.rollno);
            checkBox = (CheckBox) itemView.findViewById(R.id.check);

        }
    }
}
