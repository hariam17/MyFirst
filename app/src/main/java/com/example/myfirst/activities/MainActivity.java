package com.example.myfirst.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirst.R;
import com.example.myfirst.db.DbHelper;

public class MainActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private EditText etName, etNim;
    private Button btnSave, btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi DbHelper dan View
        dbHelper = new DbHelper(this);
        etName = findViewById(R.id.edt_name);
        etNim = findViewById(R.id.edt_nim);
        btnSave = findViewById(R.id.btn_submit);
        btnList = findViewById(R.id.btn_list);

        // Set onClickListener untuk tombol simpan
        btnSave.setOnClickListener(v -> saveUser());

        // Set onClickListener untuk tombol list
        btnList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListStudentsActivity.class);
            startActivity(intent);
        });
    }

    private void saveUser() {
        String nim = etNim.getText().toString().trim();
        String name = etName.getText().toString().trim();

        // Validasi input
        if (nim.isEmpty()) {
            Toast.makeText(MainActivity.this, "Error: Nim harus diisi!", Toast.LENGTH_SHORT).show();
        } else if (name.isEmpty()) {
            Toast.makeText(MainActivity.this, "Error: Nama harus diisi!", Toast.LENGTH_SHORT).show();
        } else {
            long result = dbHelper.addUserDetail(nim, name);
            if (result != -1) {
                etName.setText("");
                etNim.setText("");
                Toast.makeText(MainActivity.this, "Simpan berhasil!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Simpan gagal!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
