package com.example.preparelectures;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

public class profieSegment extends Fragment
{
    private TextView name;
    private TextView rollNo;
    private TextView check;
    private ImageView profileImage;
    private SharedPreferences sharedPreferences;
    private StudentsProfile profile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.profile_segment, container, false);
        linkObjects(view);
        getStudent();
        StorageReference storageReference= FirebaseStorage.getInstance().getReference("students/"+profile.getUid()+".jpeg");
        Toast.makeText(getActivity(),profile.getUid(),Toast.LENGTH_LONG).show();
        name.setText(profile.getFirstName()+" "+ profile.getLastName());
        rollNo.setText(profile.getRollNo());
        check.setText(Boolean.toString(profile.isCheck()));
        //GlideApp.with(this).load(storageReference).into(profileImage);
        return view;
    }
    void getStudent()
    {
        sharedPreferences=getActivity().getSharedPreferences("MyFile",Context.MODE_PRIVATE);
        String json=sharedPreferences.getString("StudentProfile","");
        profile=new Gson().fromJson(json, StudentsProfile.class);
    }

    void linkObjects(View view) {
        name = (TextView) view.findViewById(R.id.profile_name);
        rollNo = view.findViewById(R.id.profile_rollno);
        check= view.findViewById(R.id.checkNext);
        profileImage=view.findViewById(R.id.profile);

    }
}
