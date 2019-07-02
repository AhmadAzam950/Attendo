package com.example.preparelectures;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class Login_Page_Activity extends AppCompatActivity {

    private Button submit;
    private EditText emailText;
    private EditText passwordText;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__page);
        linkObjects();
        checkAlreadyLogin();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();

            }
        });
    }
    void checkAlreadyLogin()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        SharedPreferences sharedPreferences = getSharedPreferences("MyFile",MODE_PRIVATE);

        if (user != null) {

            String t=sharedPreferences.getString("TeachersProfile", "");
            String x=sharedPreferences.getString("StudentProfile", "");

            if (t != "") {
                Intent I = new Intent(Login_Page_Activity.this, TeacherFirstActivity.class);
                startActivity(I);
                finish();

            } else if (x != "") {
                Intent I = new Intent(Login_Page_Activity.this, StudentFirstActivity.class);
                startActivity(I);
                finish();
            }
            else
            {

            }
        } else {

        }
    }
    void linkObjects() {
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        submit = findViewById(R.id.submitButton);
        emailText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);
    }

    private void userLogin() {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("User Login");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    DocumentReference ref = db.collection("students").document(firebaseAuth.getUid());
                    ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            if (e == null) {
                                if (documentSnapshot.exists()) {
                                    Intent I = new Intent(Login_Page_Activity.this, StudentFirstActivity.class);
                                    startActivity(I);
                                    progressDialog.dismiss();
                                    finish();

                                } else {
                                    Intent I = new Intent(Login_Page_Activity.this, TeacherFirstActivity.class);
                                    startActivity(I);
                                    progressDialog.dismiss();
                                    finish();

                                }
                            }
                        }
                    });

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
