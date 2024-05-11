package com.example.librarymgtapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Publisher extends AppCompatActivity {

    EditText publisherName, address, phoneNumber;
    RecyclerView recyclerView;
    FloatingActionButton addButton, updateButton, deleteButton;

    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);

        // Initialize views
        publisherName = findViewById(R.id.publisher_name);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phone_number);
        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.add);
        updateButton = findViewById(R.id.update);
        deleteButton = findViewById(R.id.delete);

        // Set up RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Set click listeners for floating action buttons
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPublisher();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatePublisher();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deletePublisher();
            }
        });
    }

    private void addPublisher() {
        // Retrieve data from EditText fields
        String name = publisherName.getText().toString().trim();
        String publisherAddress = address.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();

        // Check if any of the fields are empty
        if (name.isEmpty() || publisherAddress.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform database operation to add a new publisher
        MyDatabaseHelper myDB = new MyDatabaseHelper(this);
        boolean isInserted = myDB.insertPublisher(name, publisherAddress, phone);

        // Check if insertion was successful
        if (isInserted) {
            Toast.makeText(this, "Publisher added successfully", Toast.LENGTH_SHORT).show();
            // Clear EditText fields after successful addition
            publisherName.getText().clear();
            address.getText().clear();
            phoneNumber.getText().clear();
        } else {
            Toast.makeText(this, "Failed to add publisher", Toast.LENGTH_SHORT).show();
        }
    }


    private void updatePublisher() {

        String name = publisherName.getText().toString();
        String addresss = address.getText().toString();
        String phone = phoneNumber.getText().toString();

        boolean isUpdated = myDB.updatePublisher(name, addresss, phone);
        if(isUpdated){
            Toast.makeText(Publisher.this, "Publisher updated successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Publisher.this, "Failed to update publisher", Toast.LENGTH_SHORT).show();
        }
    }


    private void deletePublisher() {
        String name = publisherName.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Publisher");
        builder.setMessage("Are you sure you want to delete this publisher?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean isDeleted = myDB.deletePublisher(name);
                if(isDeleted){
                    Toast.makeText(Publisher.this, "Publisher deleted successfully", Toast.LENGTH_SHORT).show();
                    // Clear EditText fields after deletion
                    publisherName.setText("");
                    phoneNumber.setText("");
                    address.setText("");
                    address.setText("");
                }else{
                    Toast.makeText(Publisher.this, "Failed to delete publisher", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });
        builder.create().show();
    }

}
