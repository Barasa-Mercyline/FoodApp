package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.databinding.ActivityCategoryBinding;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.categorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.breakfast.isChecked()){
                    Intent intent=new Intent(CategoryActivity.this,CustomerDashboardActivity.class);
                   startActivity(intent );
                }
                else if(binding.lunch.isChecked()){
                    Intent intent=new Intent(CategoryActivity.this,CustomerDashboardActivity.class);
                    startActivity(intent );

                }
                else if(binding.dinner.isChecked()){
                    Intent intent=new Intent(CategoryActivity.this,CustomerDashboardActivity.class);
                    startActivity(intent );

                }
                else {
                    Toast.makeText(CategoryActivity.this, "Select the category of food You want to Apply", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
}