package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabaseRef;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=binding.edtLoginEmail.getText().toString();
                String password=binding.edtLoginPassword.getText().toString();

                if (email.isEmpty()){
                    binding.edtLoginEmail.setError("Empty Email");
                }
                else if (password.isEmpty()){
                    binding.edtLoginPassword.setError("Empty Password");
                }
                else
                {
                    binding.loginProgressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Successfully login",Toast.LENGTH_SHORT).show();
                                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot i : snapshot.getChildren()){
                                            User user = i.getValue(User.class);

                                            Log.d(TAG, "onDataChange: "+user.getAccType());


                                            if (email.equals(user.getEmail())){
                                                if (user.getAccType().equals("Admin")){
                                                    binding.loginProgressBar.setVisibility(View.VISIBLE);
                                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }else if (user.getAccType().equals("User")){
                                                    binding.loginProgressBar.setVisibility(View.VISIBLE);
                                                    Intent intent = new Intent(LoginActivity.this, CustomerDashboardActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getApplicationContext(),"An error : "+error.getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                });






                            }else {
                                Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                binding.loginProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });

        binding.textViewDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}