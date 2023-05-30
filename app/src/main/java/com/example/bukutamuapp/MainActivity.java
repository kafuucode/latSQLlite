package com.example.bukutamuapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName;
    private Spinner spinnerStatus;
    private Button buttonAdd;
    private ListView listViewGuests;

    private GuestDataSource dataSource;
    private ArrayAdapter<GuestModel> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewGuests = findViewById(R.id.listViewGuests);

        dataSource = new GuestDataSource(this);
        dataSource.open();

        setupSpinner();
        setupListView();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                int status = spinnerStatus.getSelectedItemPosition();

                GuestModel guest = new GuestModel();
                guest.setName(name);
                guest.setStatus(status);

                dataSource.addGuest(guest);

                adapter.add(guest);
                adapter.notifyDataSetChanged();

                editTextName.setText("");
                spinnerStatus.setSelection(0);

                Toast.makeText(MainActivity.this, "Tamu ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });

        listViewGuests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GuestModel guest = adapter.getItem(position);

                // Ubah status tamu menjadi status berikutnya
                int newStatus = (guest.getStatus() + 1) % 3;
                guest.setStatus(newStatus);

                dataSource.updateGuest(guest);
                adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Status tamu diperbarui", Toast.LENGTH_SHORT).show();
            }
        });

        listViewGuests.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                GuestModel guest = adapter.getItem(position);

                dataSource.deleteGuest(guest);
                adapter.remove(guest);
                adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Tamu dihapus", Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

    private void setupSpinner() {
        String[] statuses = {"Belum Datang", "Datang", "Berhalangan Hadir"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(spinnerAdapter);
    }

    private void setupListView() {
        List<GuestModel> guests = dataSource.getAllGuests();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, guests);
        listViewGuests.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
