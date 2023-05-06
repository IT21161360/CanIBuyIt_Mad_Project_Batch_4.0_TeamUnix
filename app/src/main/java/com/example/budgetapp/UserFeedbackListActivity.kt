package com.example.budgetapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class UserFeedbackListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_feedback_list);

//        val database = Firebase.database("https://budgetapp-c421a-default-rtdb.firebaseio.com")
//        val myButton = findViewById<Button>(R.id.feedbackSubmitButton)
//        myButton.setOnClickListener{
//            Log.d("CLICKED","clicked")
//            // Write a message to the database
//            val myRef1 = database.getReference("messageRefNew")
//            myRef1.setValue("Hello, World New Ref")
//            Toast.makeText(this@UserFeedbackListActivity
//                ,"Test Message",Toast.LENGTH_LONG).show();
//        }

//        // Update a message to the database
//        val database = Firebase.database("https://budgetapp-c421a-default-rtdb.firebaseio.com")
//        val myRef = database.getReference("message")
//        myRef.setValue("Hello, World!1234")

//        // Write a message to the database
//        val myRef1 = database.getReference("messageRef")
//        myRef1.setValue("Hello, World New Ref")

//        // Read from the database
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue()
//                Log.d("TAG_DATA", "Value is: $value")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w("TAG_ERROR", "Failed to read value.", error.toException())
//            }
//        })
    }
}