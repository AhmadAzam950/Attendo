package com.example.preparelectures;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StudentHomeFragment extends Fragment {
    private Button btn;
    private Button btn1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.student_home_segment, container, false);
        linkObjects(view);
        attachListener(view);
        return view;
    }

    void linkObjects(View view) {
        btn1 = view.findViewById(R.id.bt1n);

    }

    public void attachListener(View view) {

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Scanner.class));
            }
        });
    }

}
