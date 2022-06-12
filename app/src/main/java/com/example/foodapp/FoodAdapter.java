package com.example.foodapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewholder> {

    public List<Food> foodList;
    public RecyclerViewClickInterface recyclerViewClickInterface;

    public FoodAdapter(List<Food> foodList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.foodList = foodList;
        this.recyclerViewClickInterface= recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        Food food = foodList.get(position);

        holder.foodName.setText(food.getFoodName());
        holder.foodPrice.setText(food.getFoodPrice());
        Glide.with(holder.foodImage.getContext()).load(food.getImageUrl()).into(holder.foodImage);

        holder.itemView.setTag("position");
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = (int) view.getTag(Integer.parseInt("position"));// this will give you
//                //the position of the row which will be tapped
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder{

        ImageView foodImage, addToCartImage;
        TextView foodName, foodPrice;
        Button btn;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
//            addToCartImage = itemView.findViewById(R.id.food_add_cart_imageView);
            foodName = itemView.findViewById(R.id.food_name_textView);
            foodPrice = itemView.findViewById(R.id.food_price_textView);
            foodImage = itemView.findViewById(R.id.food_imageView);
            btn=itemView.findViewById(R.id.addToCart);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   recyclerViewClickInterface.onlongItemClick(getBindingAdapterPosition());
                   return true;
                }
            });

        }
    }

    public void filterList(ArrayList<Food> filteredList) {
        foodList=filteredList;
        notifyDataSetChanged();
    }
}
