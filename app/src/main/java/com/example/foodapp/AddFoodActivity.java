package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodapp.databinding.ActivityAddFoodBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

public class AddFoodActivity extends AppCompatActivity {
ActivityAddFoodBinding binding;

    private Uri imageUri;

    ImageView choose,Before;
    EditText FdName,fdPrice,fdqty;
    Button SaveDetails;
    ProgressBar progressBar;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Request_Code=10;


    private StorageTask msaveItem;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Before=findViewById(R.id.before);

        choose=findViewById(R.id.choose);
        FdName=findViewById(R.id.foodName);
        fdPrice=findViewById(R.id.foodPrice);
        fdqty=findViewById(R.id.foodQuantity);
        SaveDetails=findViewById(R.id.buttonSave);
        progressBar=findViewById(R.id.progressBar);
        storageReference= FirebaseStorage.getInstance().getReference("Category");
        databaseReference= FirebaseDatabase.getInstance().getReference("Category");

        Before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddFoodActivity.this,AdminActivity.class);
                startActivity(intent);

            }
        });

      choose.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent();
               intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(intent,"Select Image"),Request_Code);
           }
       });

      SaveDetails.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(msaveItem !=null && msaveItem.isInProgress()){
                   Toast.makeText(AddFoodActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
               }
               else{
                   Upload(imageUri);
               }

           }
       });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Request_Code && resultCode== RESULT_OK && data !=null && data.getData() !=null)
        {
            imageUri=data.getData();

            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
               binding.choose.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public String GetExtension(Uri uri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public int Upload(Uri uri) {
        if(imageUri !=null){

           progressBar.setVisibility(View.VISIBLE);
           final  StorageReference storageReference1=storageReference.child(System.currentTimeMillis()+ "." +GetExtension(imageUri));
            msaveItem= storageReference1.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String FoodName=FdName.getText().toString().trim();
                                    String FoodPrice=fdPrice.getText().toString().trim();
                                    String FoodQty=fdqty.getText().toString().trim();
                                    FdName.getText().clear();
                                    fdPrice.getText().clear();
                                    fdqty.getText().clear();
                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(AddFoodActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();


                                    Food food =new Food(FoodName,FoodPrice,FoodQty,uri.toString());

                                    String ImageUpload=databaseReference.push().getKey();
                                    databaseReference.child(ImageUpload).setValue(food);

                                }
                            });

//
                        }
                    });

        }

        return 0;
    }


}