package com.example.librarymgtapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Branch extends AppCompatActivity {

    EditText branch_id, branch_name, branch_addrs;
    RecyclerView recyclerView;
    FloatingActionButton delete, update, add;

    MyDatabaseHelper myDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        branch_id = findViewById(R.id.branch_id);
        branch_name = findViewById(R.id.branch_name);
        branch_addrs =findViewById (R.id.branch_addrs);

        add = findViewById(R.id.add);
        update = findViewById (R.id.update);
        delete = findViewById (R.id.delete);
        recyclerView = findViewById (R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager(layoutManager);

        // Set click listeners for floating action buttons
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to add a new publisher
                addBranch();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to update publisher data
                updateBranch();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to delete publisher data
                deleteBranch();
            }
        });


    }

    private void addBranch() {
        // Get the branch ID, name, and address from EditText fields
        String branchId = branch_id.getText().toString().trim();
        String branchName = branch_name.getText().toString().trim();
        String branchAddress = branch_addrs.getText().toString().trim();

        // Check if any of the fields are empty
        if (branchId.isEmpty() || branchName.isEmpty() || branchAddress.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert the new branch data into the database
        MyDatabaseHelper myDB = new MyDatabaseHelper(this);
        boolean isInserted = myDB.insertBranch(branchId, branchName, branchAddress);

        // Check if insertion was successful
        if (isInserted) {
            Toast.makeText(this, "Branch added successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Failed to add branch", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateBranch() {
        String branchId = branch_id.getText().toString().trim();
        String branchName = branch_name.getText().toString().trim();
        String branchAddress = branch_addrs.getText().toString().trim();

        if (branchId.isEmpty() || branchName.isEmpty() || branchAddress.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            boolean isUpdated = myDB.updateBranch(branchId, branchName, branchAddress);
            if (isUpdated) {
                Toast.makeText(this, "Branch updated successfully", Toast.LENGTH_SHORT).show();
                // You can perform any additional actions here after updating the branch
            } else {
                Toast.makeText(this, "Failed to update branch", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void deleteBranch() {
        String branchId = branch_id.getText().toString().trim();

        if (branchId.isEmpty()) {
            Toast.makeText(this, "Please enter a branch ID", Toast.LENGTH_SHORT).show();
        } else {
            boolean isDeleted = myDB.deleteBranch(branchId);
            if (isDeleted) {
                Toast.makeText(this, "Branch deleted successfully", Toast.LENGTH_SHORT).show();
                // You can perform any additional actions here after deleting the branch
            } else {
                Toast.makeText(this, "Failed to delete branch", Toast.LENGTH_SHORT).show();
            }
        }
    }


}