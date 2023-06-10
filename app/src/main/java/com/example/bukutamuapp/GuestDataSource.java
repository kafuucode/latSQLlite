package com.example.bukutamuapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GuestDataSource {
    private SQLiteDatabase database;
    private final DatabaseHelper dbHelper;

    public GuestDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //add guest but dont empty the name
    public void addGuest(GuestModel guest) {
        ContentValues values = new ContentValues();
        values.put("name", guest.getName());
        values.put("status", guest.getStatus());

        database.insert("Guest", null, values);
    }

    public List<GuestModel> getAllGuests() {
        List<GuestModel> guests = new ArrayList<>();

        Cursor cursor = database.query(
                "Guest",
                null,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int status = cursor.getInt(2);
            GuestModel guest = new GuestModel(id, name, status);
            guests.add(guest);

            cursor.moveToNext();
        }

        cursor.close();
        return guests;
    }

    public void updateGuest(GuestModel guest) {
        ContentValues values = new ContentValues();
        values.put("name", guest.getName());
        values.put("status", guest.getStatus());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(guest.getId())};

        database.update("Guest", values, whereClause, whereArgs);
    }

    public void deleteGuest(GuestModel guest) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(guest.getId())};

        database.delete("Guest", whereClause, whereArgs);
    }
}
