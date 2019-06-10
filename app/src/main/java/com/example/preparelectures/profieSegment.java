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
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class profieSegment extends Fragment
{
    private TextView name;
    private TextView rollNo;
    private SharedPreferences sharedPreferences;
    private studentProfile profile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.profile_segment, container, false);
        linkObjects(view);
        sharedPreferences=getActivity().getSharedPreferences("Yo",Context.MODE_PRIVATE);
        String json=sharedPreferences.getString("profile","");
        profile=new Gson().fromJson(json,studentProfile.class);
        name.setText(profile.getFirstName()+" "+ profile.getLastName());
        rollNo.setText(profile.getRollNo());
        return view;
    }
    void linkObjects(View view) {
        name = (TextView) view.findViewById(R.id.profile_name);
        rollNo = view.findViewById(R.id.profile_rollno);
    }
}
