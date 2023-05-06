package com.example.budgetapp

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetapp.config.FirebaseHelper
import com.example.budgetapp.entity.Feedback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson


class FeedbackActivity : AppCompatActivity() {
    private val firebaseHelper = FirebaseHelper();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
        setContentView(R.layout.activity_feedback);

        var count: Long = 0;
        val feedbackList: DatabaseReference = this.firebaseHelper.getData("feedbacks")
        feedbackList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                count = snapshot.childrenCount;
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException() // Never ignore errors
            }
        })

        val myButton = findViewById<Button>(R.id.feedbackSubmitButton)
        val feedbackInput = findViewById<TextView>(R.id.feedbackInput)
        val ratingInput = findViewById<RatingBar>(R.id.rattingBarInput)
        myButton.setOnClickListener{
            val feedback = Feedback()
            feedback.setFeedback(feedbackInput.text.toString())
            feedback.setRating(ratingInput.rating)
            feedback.setShopName("Keels Super")
            this.firebaseHelper.setData(this.firebaseHelper.getDbInsertOrUpdateUrl("feedbacks",count),Gson().toJson(feedback));
            Toast.makeText(this@FeedbackActivity
                ,"Feedback Added Successfully",Toast.LENGTH_LONG).show();
        }

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