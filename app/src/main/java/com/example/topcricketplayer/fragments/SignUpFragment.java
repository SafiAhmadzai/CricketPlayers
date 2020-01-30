package com.example.topcricketplayer.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.topcricketplayer.R;
import com.example.topcricketplayer.activities.MainActivity;
import com.example.topcricketplayer.utilities.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {

    Button btnSignUp;
    EditText etEmail,etPassword,etConfirmPassword;

    TextInputLayout textInputLayout;
    SessionManager sessionManager;

    FirebaseAuth mAuth;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth=FirebaseAuth.getInstance();
        sessionManager=new SessionManager(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);


        btnSignUp=view.findViewById(R.id.btn_signup);
        textInputLayout=view.findViewById(R.id.tilEmail);

        btnSignUp.setOnClickListener(this);

        etEmail=view.findViewById(R.id.etEmail);
        etPassword=view.findViewById(R.id.etPassword);
        etConfirmPassword=view.findViewById(R.id.input_reEnterPassword);




    return view;
    }

    @Override
    public void onClick(View v) {


        String email=etEmail.getText().toString();
        String password=etPassword.getText().toString();
        String confPassword=etConfirmPassword.getText().toString();

        if ((!TextUtils.isEmpty(password) &&!TextUtils.isEmpty(confPassword) )&& !TextUtils.isEmpty(email)){
            if (password.equals(confPassword)){
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent=new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("key",sessionManager.getFirstVisit());
                            startActivity(intent);
                            getActivity().finish();

                        }
                    }
                });

            }  else {
                Toast.makeText(getActivity(), "Password and Comfirm are not the same", Toast.LENGTH_SHORT).show();

            }
        }else {

            textInputLayout.setError("Email no");
            Toast.makeText(getActivity(), "Please fill the required fields", Toast.LENGTH_SHORT).show();

        }


    }
}
