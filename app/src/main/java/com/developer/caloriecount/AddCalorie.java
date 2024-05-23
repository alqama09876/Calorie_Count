package com.developer.caloriecount;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.developer.caloriecount.Database.DatabaseHelper;

import java.util.Calendar;

public class AddCalorie extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    ImageView iv_back;
    EditText edtName, edtWeight, edtDensity, edtQuantity, edtDate;
    Button btnAdd;
    private Calendar selectedDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calorie);

        mDatabaseHelper = new DatabaseHelper(this);
        edtName = findViewById(R.id.edt_name);
        edtWeight = findViewById(R.id.edt_weight);
        edtDensity = findViewById(R.id.edt_density);
        edtQuantity = findViewById(R.id.edt_quantity);
        edtDate = findViewById(R.id.edt_date);
        iv_back = findViewById(R.id.iv_back);
        btnAdd = findViewById(R.id.buttonAdd);
        selectedDate = Calendar.getInstance();

        edtDate.setFocusable(false);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                long selectedTimestamp = selectedDate.getTimeInMillis();
                String name = edtName.getText().toString();
                int weight = Integer.parseInt(edtWeight.getText().toString());
                double density = Double.parseDouble(edtDensity.getText().toString());
                int quantity = Integer.parseInt(edtQuantity.getText().toString());
                double totalCalories = calculateCalories(weight, density, quantity);
                String date = edtDate.getText().toString();
                boolean insertData = mDatabaseHelper.addData(name, weight, density, quantity, totalCalories, date);
                if (insertData) {
                    toastMessage("Data Successfully Inserted!");
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    edtName.setText("");
                    edtWeight.setText("");
                    edtDensity.setText("");
                    edtQuantity.setText("");
                    edtDate.setText("");
                } else {
                    toastMessage("Something went wrong.");
                }
            }
        });
    }

    private void openDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update selectedDate when the user selects a new date
                        selectedDate.set(year, monthOfYear, dayOfMonth);
                        edtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private double calculateCalories(int weight, double density, int quantity) {
        return weight * density * quantity;
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    private void displayData() {
        Cursor data = mDatabaseHelper.getData();
    }
}
