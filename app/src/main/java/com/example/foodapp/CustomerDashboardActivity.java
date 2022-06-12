package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.foodapp.databinding.ActivityCustomerDashboardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.internal.DiskLruCache;

import java.util.ArrayList;

public class CustomerDashboardActivity extends AppCompatActivity implements RecyclerViewClickInterface {
    ActivityCustomerDashboardBinding binding;

    private DatabaseReference mDatabaseReference;
    private FoodAdapter adapter;
    ArrayList<Food> foodArrayList = new ArrayList<>();

    private static final String TAG = "CustomerDashboardActivi";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        RecyclerView recyclerView=findViewById(R.id.food_Recylerview);
        adapter=new FoodAdapter(foodArrayList,this);

        getFoods();

        binding.searchItemBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });




    }




     public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_option,menu);
        return  true;

    }

    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case R.id.menu_notif:
                Toast.makeText(this, "Motification clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_setting:
                Toast.makeText(this, "setting clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_share:
                Toast.makeText(this, "share clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_logout:
                Intent intent=new Intent(CustomerDashboardActivity.this,LoginActivity.class);
                Toast.makeText(this, "You have Successfully  logout", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
        }
        return  super.onOptionsItemSelected(item);
    }





    private void getFoods(){

        binding.foodProgressBar.setVisibility(View.VISIBLE);
        mDatabaseReference.child("Category").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Log.d(TAG, "onDataChange: Data exist: "+snapshot);
                    for (DataSnapshot i : snapshot.getChildren()){
                        Food food = i.getValue(Food.class);
                        foodArrayList.add(food);
                        Log.d(TAG, "onDataChange: " +food.getFoodName());

                    }
                    binding.foodProgressBar.setVisibility(View.GONE);

//                    adapter = new FoodAdapter(foodArrayList,this);
                    adapter=new FoodAdapter(foodArrayList,CustomerDashboardActivity.this);
                    adapter.notifyDataSetChanged();
                    binding.foodRecylerview.setAdapter(adapter);
                }else{
                    binding.foodProgressBar.setVisibility(View.GONE);
                    Log.d(TAG, "onDataChange: Data does not exist");
                    Toast.makeText(CustomerDashboardActivity.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private  void filter(String text){
        ArrayList<Food> filteredList=new ArrayList<>();
        for(Food item :foodArrayList)
        {
            if(item.getFoodName().toLowerCase().contains(text.toLowerCase()))
            {
               filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, ""+ foodArrayList.get(position), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onlongItemClick(int position) {
        foodArrayList.remove(position);
        adapter.notifyItemRemoved(position);

    }
}