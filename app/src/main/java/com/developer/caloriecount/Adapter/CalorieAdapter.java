package com.developer.caloriecount.Adapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.caloriecount.DisplayData;
import com.developer.caloriecount.Model.CalorieItem;
import com.developer.caloriecount.R;

import java.util.List;

public class CalorieAdapter extends RecyclerView.Adapter<CalorieAdapter.CalorieViewHolder> {
    private List<CalorieItem> calorieItemList;

    public CalorieAdapter(List<CalorieItem> calorieItemList) {
        this.calorieItemList = calorieItemList;
    }

    @NonNull
    @Override
    public CalorieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calorie, parent, false);
        return new CalorieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalorieViewHolder holder, int position) {
        CalorieItem calorieItem = calorieItemList.get(position);
        holder.bind(calorieItem);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalorieItem clickedItem = calorieItemList.get(holder.getAdapterPosition());

                Bundle bundle = new Bundle();
                bundle.putString("id", clickedItem.getId());
                bundle.putString("name", clickedItem.getName());
                bundle.putInt("weight", clickedItem.getWeight());
                bundle.putDouble("density", clickedItem.getDensity());
                bundle.putInt("quantity", clickedItem.getQuantity());
                bundle.putDouble("totalCalories", clickedItem.getTotalCalories());
                bundle.putString("dateCreated", clickedItem.getDateCreated());
                Intent intent = new Intent(v.getContext(), DisplayData.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return calorieItemList.size();
    }

    public static class CalorieViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtCalories;
        LinearLayout card;
        public CalorieViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCalories = itemView.findViewById(R.id.txtCalories);
            card = itemView.findViewById(R.id.card);
        }

        public void bind(CalorieItem calorieItem) {
            txtName.setText(calorieItem.getName());
            txtCalories.setText(String.valueOf(calorieItem.getTotalCalories()));
        }
    }
}
