package com.example.librarymgtapp;

import android.content.ContentValues;
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

public class Member extends AppCompatActivity {

    EditText card_number, membr_Name, membr_addrs, membr_phone, unpaid_dues_no;
    RecyclerView recyclerView;
    FloatingActionButton delete, update, add;

    MyDatabaseHelper myDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        card_number = findViewById(R.id.card_number);
        membr_Name = findViewById(R.id.membr_Name);
        membr_addrs =findViewById (R.id.membr_addrs);
        membr_phone = findViewById(R.id.membr_phone);
        unpaid_dues_no =findViewById (R.id.unpaid_dues_no);

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
                addMember();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to update publisher data
                updateMember();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to delete publisher data
                deleteMember();
            }
        });


    }

    private void addMember() {
        // Retrieve data from EditText fields
        String cardNumber = card_number.getText().toString();
        String name = membr_Name.getText().toString();
        String address = membr_addrs.getText().toString();
        String phone = membr_phone.getText().toString();
        String dues = unpaid_dues_no.getText().toString();

        // Validate input fields if needed
        if (cardNumber.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty() || dues.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Now you can perform database operations to add a new member
        MyDatabaseHelper myDB = new MyDatabaseHelper(Member.this);
        boolean success = myDB.insertMember(cardNumber, name, address, phone, dues);

        if (success) {
            Toast.makeText(this, "Member added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add member", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateMember() {
        String cardNumber = card_number.getText().toString().trim();
        String memberName = membr_Name.getText().toString().trim();
        String memberAddress = membr_addrs.getText().toString().trim();
        String memberPhone = membr_phone.getText().toString().trim();
        String unpaidDues = unpaid_dues_no.getText().toString().trim();

        if (cardNumber.isEmpty() || memberName.isEmpty() || memberAddress.isEmpty() || memberPhone.isEmpty() || unpaidDues.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        MyDatabaseHelper myDB = new MyDatabaseHelper(this);
        boolean isUpdated = myDB.updateMember(cardNumber, memberName, memberAddress, memberPhone, unpaidDues);
        if (isUpdated) {
            Toast.makeText(this, "Member updated successfully", Toast.LENGTH_SHORT).show();
            // Refresh RecyclerView or perform any other actions as needed
        } else {
            Toast.makeText(this, "Failed to update member", Toast.LENGTH_SHORT).show();
        }
    }


    private void deleteMember() {
        String cardNumber = card_number.getText().toString().trim();

        if (cardNumber.isEmpty()) {
            Toast.makeText(this, "Please enter card number to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        MyDatabaseHelper myDB = new MyDatabaseHelper(this);
        boolean isDeleted = myDB.deleteMember(cardNumber);
        if (isDeleted) {
            Toast.makeText(this, "Member deleted successfully", Toast.LENGTH_SHORT).show();
            // Refresh RecyclerView or perform any other actions as needed
        } else {
            Toast.makeText(this, "Failed to delete member", Toast.LENGTH_SHORT).show();
        }


        }
    }
