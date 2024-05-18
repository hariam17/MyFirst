package com.example.myfirst.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirst.R;
import com.example.myfirst.db.DbHelper;
import com.example.myfirst.model.Student;

public class UpdateActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private EditText etName, etNim;
    private Button btnSave;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Inisialisasi DbHelper dan View
        dbHelper = new DbHelper(this);
        etName = findViewById(R.id.edt_name);
        etNim = findViewById(R.id.edt_nim);
        btnSave = findViewById(R.id.btn_submit);

        // Ambil data dari Intent
        Intent intent = getIntent();
        student = (Student) intent.getSerializableExtra("user");

        // Set data ke EditText
        etName.setText(student.getName());
        etNim.setText(student.getNim());

        // Set OnClickListener untuk tombol simpan
        btnSave.setOnClickListener(this::updateUser);
    }

    private void updateUser(View view) {
        String name = etName.getText().toString().trim();
        String nim = etNim.getText().toString().trim();

        // Update data di database
        dbHelper.updateUser(student.getId(), nim, name);
        Toast.makeText(UpdateActivity.this, "Update berhasil!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
