package com.example.preparelectures;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Adaptor extends FirestoreRecyclerAdapter<StudentsProfile,Adaptor.MyViewHolder> {
    String[] names;
    String[] roll;
    boolean[] check;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    HashMap<String,Object> map=new HashMap<>();
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Adaptor(@NonNull FirestoreRecyclerOptions<StudentsProfile> options) {
        super(options);


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View txt = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customlayout, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(txt);
        return viewHolder;

    }
    void updateItemTrue()
    {
        for(int  i=0;i<getItemCount();i++)
        {
            getSnapshots().getSnapshot(i).getReference().update("check",true);
        }
    }
    void updateItemFalse()
    {
        for(int  i=0;i<getItemCount();i++)
        {
            getSnapshots().getSnapshot(i).getReference().update("check",false);
        }
    }
    int getItemCountt()
    {
        return getItemCount();
    }

    /*@NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View txt = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customlayout, viewGroup, false);
            MyViewHolder viewHolder = new MyViewHolder(txt);
            return viewHolder;
        }
    */
   /* @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.txt.setText(names[i]);
        viewHolder.txt2.setText(roll[i]);
        viewHolder.checkBox.setChecked(check[i]);

    }
*/
    /*@Override
    public int getItemCount() {
        return names.length;
    }
*/
    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull StudentsProfile model) {
        holder.checkBox.setChecked(model.isCheck());
        holder.txt.setText(model.getFirstName()+" "+model.getLastName());
        holder.txt2.setText(model.getRollNo());
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        TextView txt2;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt = (TextView) itemView.findViewById(R.id.name);
            txt2 = (TextView) itemView.findViewById(R.id.rollno);
            checkBox = (CheckBox) itemView.findViewById(R.id.check);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positon=getAdapterPosition();
                    StudentsProfile s=getSnapshots().getSnapshot(positon).toObject(StudentsProfile.class);
                    if(s.isCheck())
                    {
                        getSnapshots().getSnapshot(positon).getReference().update("check",false);
                    }
                    else
                    {
                        getSnapshots().getSnapshot(positon).getReference().update("check",true);
                    }
                }
            });

        }
    }
}
