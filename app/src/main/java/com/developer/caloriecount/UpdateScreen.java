package com.developer.caloriecount;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.developer.caloriecount.Database.DatabaseHelper;

import java.util.Calendar;

public class UpdateScreen extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    EditText edtName, edtWeight, edtDensity, edtQuantity, edtDate;
    private String itemId;
    ImageView iv_back;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_screen);
        iv_back = findViewById(R.id.iv_back);
        dbHelper = new DatabaseHelper(this);
        selectedDate = Calendar.getInstance();
        edtName = findViewById(R.id.edt_name);
        edtWeight = findViewById(R.id.edt_weight);
        edtDensity = findViewById(R.id.edt_density);
        edtQuantity = findViewById(R.id.edt_quantity);
        edtDate = findViewById(R.id.edt_date);

        edtDate.setFocusable(false);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            itemId = extras.getString("id");
            String name = extras.getString("name");
            int weight = extras.getInt("weight");
            double density = extras.getDouble("density");
            int quantity = extras.getInt("quantity");
            String dateCreated = extras.getString("dateCreated");
            edtName.setText(name);
            edtWeight.setText(String.valueOf(weight));
            edtDensity.setText(String.valueOf(density));
            edtQuantity.setText(String.valueOf(quantity));
            edtDate.setText(dateCreated);
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnUpdate = findViewById(R.id.btn_update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                long selectedTimestamp = selectedDate.getTimeInMillis();
                String newName = ((EditText) findViewById(R.id.edt_name)).getText().toString();
                int newWeight = Integer.parseInt(((EditText) findViewById(R.id.edt_weight)).getText().toString());
                double newDensity = Double.parseDouble(((EditText) findViewById(R.id.edt_density)).getText().toString());
                int newQuantity = Integer.parseInt(((EditText) findViewById(R.id.edt_quantity)).getText().toString());
                String date = ((EditText) findViewById(R.id.edt_date)).getText().toString();

                boolean updated = dbHelper.updateData(Integer.parseInt(itemId), newName, newWeight, newDensity, newQuantity, date);

                if (updated) {
                    Toast.makeText(UpdateScreen.this, "Record updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                } else {
                    Toast.makeText(UpdateScreen.this, "Failed to update record", Toast.LENGTH_SHORT).show();
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
}