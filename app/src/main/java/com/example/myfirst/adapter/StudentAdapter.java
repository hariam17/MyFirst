package com.example.myfirst.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirst.R;
import com.example.myfirst.activities.UpdateActivity;
import com.example.myfirst.db.DbHelper;
import com.example.myfirst.model.Student;

import java.io.Serializable;
import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private ArrayList<Student> listStudents = new ArrayList<>();
    private Activity activity;
    private DbHelper dbHelper;

    public StudentAdapter(Activity activity) {
        this.activity = activity;
        this.dbHelper = new DbHelper(activity);
    }

    public ArrayList<Student> getListStudents() {
        return listStudents;
    }

    public void setListStudents(ArrayList<Student> listStudents) {
        this.listStudents.clear();
        this.listStudents.addAll(listStudents);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = listStudents.get(position);
        holder.tvNim.setText(student.getNim());
        holder.tvName.setText(student.getName());

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(activity, UpdateActivity.class);
            intent.putExtra("user", student);
            activity.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Konfirmasi hapus");
            builder.setMessage("Apakah yakin akan dihapus?");
            builder.setPositiveButton("YA", (dialog, which) -> {
                dbHelper.deleteUser(student.getId());
                Toast.makeText(activity, "Hapus berhasil!", Toast.LENGTH_SHORT).show();
                updateStudentList();
            });
            builder.setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss());
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    private void updateStudentList() {
        listStudents.clear();
        listStudents.addAll(dbHelper.getAllUsers());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listStudents.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        final TextView tvNim, tvName;
        final Button btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNim = itemView.findViewById(R.id.tv_item_nim);
            tvName = itemView.findViewById(R.id.tv_item_name);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
