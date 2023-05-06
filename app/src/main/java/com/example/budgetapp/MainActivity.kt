package com.example.budgetapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.budgetapp.config.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val firebaseHelper = FirebaseHelper();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Update a message to the database
        val database = Firebase.database("https://budgetapp-c421a-default-rtdb.firebaseio.com")
        val myRef = database.getReference("message")
        myRef.setValue("Hello, World!1234")

        // Write a message to the database
        val myRef1 = database.getReference("messageRef")
        myRef1.setValue("Hello, World New Ref")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue()
                Log.d("TAG_DATA", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG_ERROR", "Failed to read value.", error.toException())
            }
        })
    }
}