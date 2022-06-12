package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity  implements
        NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout, toolbar,
                R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.AddFoodCategory:
                Toast.makeText(this, "Add food Category", Toast.LENGTH_SHORT).show();
                break;

            case R.id.AddFoodItem:
               // Toast.makeText(this, "Add food Item", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AdminActivity.this,AddFoodActivity.class);
                startActivity(intent);

                break;

            case R.id.deleteFoodItem:
                Toast.makeText(this, "Delete food item", Toast.LENGTH_SHORT).show();
                break;

            case R.id.vieworders:
                Toast.makeText(this, "View Food orders", Toast.LENGTH_SHORT).show();
                break;



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
                Intent intentlogout=new Intent(AdminActivity.this,LoginActivity.class);
                Toast.makeText(this, "You have Successfully  logout", Toast.LENGTH_SHORT).show();
                startActivity(intentlogout);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}