package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.editRegisterUserName.getText().toString();
                String regNo = binding.editRegisterRegNo.getText().toString();
                String phoneNo = binding.edtRegisterPhoneNum.getText().toString();
                String email = binding.editRegisterEmail.getText().toString();
                String
































                        password = binding.edtRegisterPassword.getText().toString();

                if (username.isEmpty()){
                    binding.editRegisterUserName.setError("Empty Username");
                }
                else if (regNo.isEmpty()){
                    binding.editRegisterEmail.setError("Empty Reg No");
                }
                else if (phoneNo.isEmpty()){
                    binding.editRegisterEmail.setError("Empty Reg No");
                }
                else if (email.isEmpty()){
                    binding.editRegisterEmail.setError("Empty Reg No");
                }
                else if (password.isEmpty()){
                    binding.edtRegisterPassword.setError("Empty Phone Number");
                }
                else if (password.length() < 6){
                    binding.edtRegisterPassword.setError("Too weak password, use 8 characters");
                }
                else{

                    binding.RegisterProgressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                String userId = firebaseAuth.getCurrentUser().getUid();
                                User user = new User(username, email, phoneNo, regNo, "User");
                                databaseReference.child(userId).setValue(user);

                                Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();

                                binding.RegisterProgressBar.setVisibility(View.INVISIBLE);


                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }else{
                                binding.RegisterProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        binding.textViewHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

