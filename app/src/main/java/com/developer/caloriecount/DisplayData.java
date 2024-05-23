package com.developer.caloriecount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.developer.caloriecount.Database.DatabaseHelper;

public class DisplayData extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    String itemId;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_data);

        TextView txtName = findViewById(R.id.txtName);
        TextView txtWeight = findViewById(R.id.txtWeight);
        TextView txtDensity = findViewById(R.id.txtDensity);
        TextView txtQuantity = findViewById(R.id.txtQuantity);
        TextView txtTotalCalories = findViewById(R.id.txtTotalCalories);
        TextView txtDate = findViewById(R.id.txtDate);
        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            itemId = extras.getString("id");
            String name = extras.getString("name");
            int weight = extras.getInt("weight");
            double density = extras.getDouble("density");
            int quantity = extras.getInt("quantity");
            double totalCalories = extras.getDouble("totalCalories");
            String dateCreated = extras.getString("dateCreated");

            txtName.setText("Food Name: " + name);
            txtWeight.setText("Weight (g): " + weight);
            txtDensity.setText("Density: " + density);
            txtQuantity.setText("Quantity: " + quantity);
            txtTotalCalories.setText("Total Calories: " + totalCalories);
            txtDate.setText("Date: " + dateCreated);
        }

        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayData.this, UpdateScreen.class);
                intent.putExtra("id", itemId);
                intent.putExtra("name", txtName.getText().toString());
                intent.putExtra("weight", Integer.parseInt(txtWeight.getText().toString().replace("Weight (g): ", "")));
                intent.putExtra("density", Double.parseDouble(txtDensity.getText().toString().replace("Density: ", "")));
                intent.putExtra("quantity", Integer.parseInt(txtQuantity.getText().toString().replace("Quantity: ", "")));
                intent.putExtra("totalCalories", Double.parseDouble(txtTotalCalories.getText().toString().replace("Total Calories: ", "")));
                intent.putExtra("dateCreated", txtDate.getText().toString());
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleted = dbHelper.deleteData(Integer.parseInt(itemId));
                if (deleted) {
                    Toast.makeText(DisplayData.this, "Item deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                } else {
                    Toast.makeText(DisplayData.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
