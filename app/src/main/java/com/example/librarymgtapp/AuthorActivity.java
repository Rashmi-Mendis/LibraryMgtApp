package com.example.librarymgtapp;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AuthorActivity extends AppCompatActivity {

    EditText book_id, author_name;
    RecyclerView recyclerView;
    FloatingActionButton delete, update, add;

    MyDatabaseHelper myDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        book_id = findViewById(R.id.book_id);
        author_name = findViewById(R.id.author_name);

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
                addBookAuthor();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to update publisher data
                updateBookAuthor();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to delete publisher data
                deleteBookAuthor();
            }
        });


    }

    private void addBookAuthor() {
        String bookId = book_id.getText().toString().trim();
        String authorName = author_name.getText().toString().trim();

        if (bookId.isEmpty() || authorName.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        boolean inserted = dbHelper.insertAuthor(bookId, authorName);
        if (inserted) {
            Toast.makeText(this, "Author added successfully", Toast.LENGTH_SHORT).show();
            book_id.setText("");
            author_name.setText("");
        } else {
            Toast.makeText(this, "Failed to add author", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateBookAuthor() {
        String bookId = book_id.getText().toString().trim();
        String authorName = author_name.getText().toString().trim();

        if (bookId.isEmpty() || authorName.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        boolean updated = myDB.updateAuthor(bookId, authorName);

        if (updated) {
            Toast.makeText(this, "Author updated successfully", Toast.LENGTH_SHORT).show();
            book_id.setText("");
            author_name.setText("");
        } else {
            Toast.makeText(this, "Failed to update author", Toast.LENGTH_SHORT).show();
        }
    }


    private void deleteBookAuthor() {
        String bookId = book_id.getText().toString().trim();

        if (bookId.isEmpty()) {
            Toast.makeText(this, "Please enter Book ID", Toast.LENGTH_SHORT).show();
            return;
        }

        MyDatabaseHelper myDB = new MyDatabaseHelper(this);
        boolean deleted = myDB.deleteAuthor(bookId);

        if (deleted) {
            Toast.makeText(this, "Author deleted successfully", Toast.LENGTH_SHORT).show();
            book_id.setText("");
            author_name.setText("");
        } else {
            Toast.makeText(this, "Failed to delete author", Toast.LENGTH_SHORT).show();
        }
    }

}