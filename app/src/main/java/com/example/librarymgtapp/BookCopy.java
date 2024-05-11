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

public class BookCopy extends AppCompatActivity {

    EditText book_id, branch_id, access_no;
    RecyclerView recyclerView;
    FloatingActionButton delete, update, add;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_copy);

        book_id = findViewById(R.id.book_id);
        branch_id = findViewById(R.id.branch_id);
        access_no =findViewById (R.id.access_no);

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
                addBookCopy();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to update publisher data
                updateBookCopy();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to delete publisher data
                deleteBookCopy();
            }
        });


    }

    private void addBookCopy(){

        // Get data from EditText fields
        String bookId = book_id.getText().toString();
        String branchId = branch_id.getText().toString();
        String accessNo = access_no.getText().toString();

        // Call the database helper method to insert the book copy
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        boolean isSuccess = dbHelper.insertBookCopy(bookId, branchId, accessNo);

    }

    private void updateBookCopy() {
        // Get the updated data from EditText fields
        String bookId = book_id.getText().toString();
        String branchId = branch_id.getText().toString();
        String accessNo = access_no.getText().toString();

        // Call the database helper method to update the book copy
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        boolean isSuccess = dbHelper.updateBookCopy(bookId, branchId, accessNo);

        if (isSuccess) {

            Toast.makeText(this, "Book copy updated successfully", Toast.LENGTH_SHORT).show();
            // show a success message or perform any other action here
        } else {

            Toast.makeText(this, "Failed to update book copy", Toast.LENGTH_SHORT).show();
            //  show an error message or perform any other action here
        }
    }


    private void deleteBookCopy() {
        // Get the book ID of the book copy to be deleted
        String bookId = book_id.getText().toString();

        // Get the branch ID of the book copy to be deleted
        String branchId = branch_id.getText().toString();

        // Get the access number of the book copy to be deleted
        String accessNo = access_no.getText().toString();

        // Call the database helper method to delete the book copy
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        boolean isSuccess = dbHelper.deleteBookCopy(bookId, branchId, accessNo);

        if (isSuccess) {

            Toast.makeText(this, "Book copy deleted successfully", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Failed to delete book copy", Toast.LENGTH_SHORT).show();
        }
    }

}