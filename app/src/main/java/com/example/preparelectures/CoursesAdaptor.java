package com.example.preparelectures;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CoursesAdaptor extends FirestoreRecyclerAdapter<courses, CoursesAdaptor.MyViewHolder> {
    String[] names;
    String[] roll;
    boolean[] check;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
private  OnItemClickListener listener;
    HashMap<String, String> map;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CoursesAdaptor(@NonNull FirestoreRecyclerOptions<courses> options) {
        super(options);
        db = FirebaseFirestore.getInstance();
       /* map = new HashMap<String, String>();
        db.collection("courses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot doc:task.getResult())
                {

                    map.put(doc.getId(), (String) doc.get("courseName"));
                    notifyDataSetChanged();
                }
            }
        });*/
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View txt = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customlayout2, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(txt);
        return viewHolder;

    }

    void updateItemTrue() {
        for (int i = 0; i < getItemCount(); i++) {
            getSnapshots().getSnapshot(i).getReference().update("check", true);
        }
    }

    void updateItemFalse() {
        for (int i = 0; i < getItemCount(); i++) {
            getSnapshots().getSnapshot(i).getReference().update("check", false);
        }
    }

    int getItemCountt() {
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
    public String getItemValue(int position)
    {
        return getSnapshots().getSnapshot(position).getId();
    }
    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder, int position, @NonNull courses model) {

        /*/db.collection("courses")
                .document(model.getCourseId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                holder.txt.setText(doc.getString("courseName"));
            }
        });
        */
      //  map.put("MC-1", "Hello");
    /*    String string = (String) map.get(model.getCourseId());
        holder.txt.setText(string);*/
    holder.txt.setText(getSnapshots().getSnapshot(position).getId());
        holder.srNo.setText("No."+Integer.toString(position+1));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        TextView srNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            srNo=(TextView)itemView.findViewById(R.id.srNo);
            txt = (TextView) itemView.findViewById(R.id.courseName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  int position=getAdapterPosition();
                  if(position!=RecyclerView.NO_POSITION&&listener!=null)
                  {
                      listener.onItemClick(getSnapshots().getSnapshot(position),position);
                  }
                }
            });

        }
       public TextView getTextView()
        {
            return txt;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot,int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener=listener;
    }
}
