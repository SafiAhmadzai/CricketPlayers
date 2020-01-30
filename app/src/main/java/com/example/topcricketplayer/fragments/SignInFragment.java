package com.example.topcricketplayer.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.topcricketplayer.R;
import com.example.topcricketplayer.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment implements View.OnClickListener {

    EditText etEmail,etPassword;
    Button btnSignIn;

    FirebaseAuth mAuth;


    public SignInFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_sign_in, container, false);

        mAuth=FirebaseAuth.getInstance();
        etEmail=view.findViewById(R.id.etEmail);
        etPassword=view.findViewById(R.id.etPassword);
        btnSignIn=view.findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(this);


    return view;
    }

    @Override
    public void onClick(View v) {

        String email=etEmail.getText().toString();
        String password=etPassword.getText().toString();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(getActivity(), "Sign iN sUCCESFULL", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    } else {
                        Toast.makeText(getActivity(), "Failed Please Tray Again", Toast.LENGTH_SHORT).show();


                    }
                }
            });

        }else {

            Toast.makeText(getActivity(), "Please enter  Email and Password", Toast.LENGTH_SHORT).show();
        }
    }
}
