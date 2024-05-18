package com.example.myfirst.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirst.R;
import com.example.myfirst.adapter.StudentAdapter;
import com.example.myfirst.db.DbHelper;
import com.example.myfirst.model.Student;

import java.util.ArrayList;

public class ListStudentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private ArrayList<Student> studentsArrayList;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_students);

        // Inisialisasi RecyclerView dan DbHelper
        recyclerView = findViewById(R.id.rview);
        dbHelper = new DbHelper(this);

        // Inisialisasi Adapter dan ArrayList
        studentsArrayList = dbHelper.getAllUsers();
        adapter = new StudentAdapter(this);
        adapter.setListStudents(studentsArrayList);

        // Set LayoutManager dan Adapter untuk RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Perbarui data saat aktivitas dilanjutkan
        studentsArrayList = dbHelper.getAllUsers();
        adapter.setListStudents(studentsArrayList);
        adapter.notifyDataSetChanged();
    }
}
