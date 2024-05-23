package com.developer.caloriecount;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.caloriecount.Adapter.CalorieAdapter;
import com.developer.caloriecount.Database.DatabaseHelper;
import com.developer.caloriecount.Model.CalorieItem;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    TextView tv_add_record;
    TextView tv_total_calories;
    RecyclerView recyclerView;
    CalorieAdapter calorieAdapter;
    EditText edt_search_item;
    ArrayList<CalorieItem> calorieItemList;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        mDatabaseHelper = new DatabaseHelper(this);

        edt_search_item = findViewById(R.id.edt_search_item);
        tv_add_record = findViewById(R.id.tv_add_record);
        tv_total_calories = findViewById(R.id.tv_remaining_calories);
        recyclerView = findViewById(R.id.rv_cal);

        edt_search_item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().trim();
                searchItem(searchText);
            }
        });

        tv_add_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddCalorie.class));
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        calorieItemList = new ArrayList<>();
        loadData();
    }

    private void searchItem(String searchText) {
        ArrayList<CalorieItem> searchedList = mDatabaseHelper.searchCalorieByName(searchText);
        calorieAdapter = new CalorieAdapter(searchedList);
        recyclerView.setAdapter(calorieAdapter);
    }

    private void loadData() {
        Cursor data = mDatabaseHelper.getData();
        double totalCalories = 0;
        if (data.getCount() != 0) {
            while (data.moveToNext()) {
                int id = data.getInt(data.getColumnIndexOrThrow("ID"));
                String name = data.getString(data.getColumnIndexOrThrow("Name"));
                int weight = data.getInt(data.getColumnIndexOrThrow("Weight"));
                double density = data.getDouble(data.getColumnIndexOrThrow("Density"));
                int quantity = data.getInt(data.getColumnIndexOrThrow("Quantity"));
                double calories = data.getDouble(data.getColumnIndexOrThrow("TotalCalories"));
                String date = data.getString(data.getColumnIndexOrThrow("DateCreated"));

                totalCalories += calories;
                CalorieItem calorieItem = new CalorieItem(String.valueOf(id), name, weight, density, quantity, calories, date);
                calorieItemList.add(calorieItem);
            }
        }

        calorieAdapter = new CalorieAdapter(calorieItemList);
        recyclerView.setAdapter(calorieAdapter);

        // Set total calories to TextView
        tv_total_calories.setText(String.format("Total Calories: %.2f", totalCalories));
    }
}
