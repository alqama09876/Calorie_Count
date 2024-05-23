package com.developer.caloriecount.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.developer.caloriecount.Model.CalorieItem;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FruitCalories.db";
    private static final String TABLE_NAME = "fruits";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "Name";
    private static final String COL_WEIGHT = "Weight";
    private static final String COL_DENSITY = "Density";
    private static final String COL_QUANTITY = "Quantity";
    private static final String COL_TOTAL_CALORIES = "TotalCalories";
    private static final String COL_DATE_CREATED = "DateCreated";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_WEIGHT + " INTEGER, " +
                COL_DENSITY + " REAL, " +
                COL_QUANTITY + " INTEGER, " +
                COL_TOTAL_CALORIES + " REAL, " +
                COL_DATE_CREATED + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name, int weight, double density, int quantity, double totalCalories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_WEIGHT, weight);
        contentValues.put(COL_DENSITY, density);
        contentValues.put(COL_QUANTITY, quantity);
        contentValues.put(COL_TOTAL_CALORIES, totalCalories);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean addData(String name, int weight, double density, int quantity, double totalCalories, String dateCreated) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_WEIGHT, weight);
        contentValues.put(COL_DENSITY, density);
        contentValues.put(COL_QUANTITY, quantity);
        contentValues.put(COL_TOTAL_CALORIES, totalCalories);
        contentValues.put(COL_DATE_CREATED, dateCreated);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public ArrayList<CalorieItem> getAllData() {
        ArrayList<CalorieItem> calorieItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                int weight = cursor.getInt(cursor.getColumnIndexOrThrow(COL_WEIGHT));
                double density = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_DENSITY));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTITY));
                double totalCalories = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TOTAL_CALORIES));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE_CREATED));
                calorieItemList.add(new CalorieItem(String.valueOf(id), name, weight, density, quantity, totalCalories, date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return calorieItemList;
    }

    public boolean updateData(int id, String name, int weight, double density, int quantity, String dateCreated) {
        SQLiteDatabase db = this.getWritableDatabase();
        double currentTotalCalories = 0.0;
        Cursor cursor = db.rawQuery("SELECT " + COL_TOTAL_CALORIES + " FROM " + TABLE_NAME + " WHERE " + COL_ID + "=?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            currentTotalCalories = cursor.getDouble(0);
        }
        cursor.close();
        double newTotalCalories = weight * density * quantity;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_WEIGHT, weight);
        contentValues.put(COL_DENSITY, density);
        contentValues.put(COL_QUANTITY, quantity);
        contentValues.put(COL_TOTAL_CALORIES, newTotalCalories);
        contentValues.put(COL_DATE_CREATED, dateCreated);
        long result = db.update(TABLE_NAME, contentValues, COL_ID + "=?", new String[]{String.valueOf(id)});
        return result != -1;
    }

    public boolean deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
        return result != -1;
    }

    @SuppressLint("Range")
    public ArrayList<CalorieItem> searchCalorieByName(String name) {
        ArrayList<CalorieItem> calorieItemList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selection = "Name LIKE ?";
        String[] selectionArgs = {"%" + name + "%"};

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                int weight = cursor.getInt(cursor.getColumnIndexOrThrow(COL_WEIGHT));
                double density = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_DENSITY));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTITY));
                double totalCalories = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TOTAL_CALORIES));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE_CREATED));
                calorieItemList.add(new CalorieItem(String.valueOf(id), itemName, weight, density, quantity, totalCalories, date));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return calorieItemList;
    }

}
