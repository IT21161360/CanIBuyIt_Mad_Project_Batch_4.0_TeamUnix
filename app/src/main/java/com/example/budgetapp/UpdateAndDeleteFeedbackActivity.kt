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
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson


class UpdateAndDeleteFeedbackActivity : AppCompatActivity() {
    private val firebaseHelper: FirebaseHelper = FirebaseHelper()

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
        setContentView(R.layout.activity_update_and_delete_feedback);

        val myButton = findViewById<Button>(R.id.updateFeedbackButton)
        val feedback = this.firebaseHelper.getData(this.firebaseHelper.getDbInsertOrUpdateUrl("feedbacks",intent.getStringExtra("reference").toString().toLong()))
        val feedbackInput = findViewById<TextView>(R.id.updateCommentInput)
        val feedbackRatingInput = findViewById<RatingBar>(R.id.rattingBarInput)

        feedback.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.getValue() != null){
                    val feedbackObject = Gson().fromJson(snapshot.getValue().toString(),Feedback::class.java)
                    feedbackInput.setText(feedbackObject.getFeedback())
                    feedbackRatingInput.rating = feedbackObject.getRating()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException() // Never ignore errors
            }
        })

        myButton.setOnClickListener{
            val feedbackObject = Feedback()
            feedbackObject.setFeedback(feedbackInput.text.toString())
            feedbackObject.setRating(feedbackRatingInput.rating)
            feedbackObject.setShopName("Keels Super")
            this.firebaseHelper.setData("feedbacks/" + intent.getStringExtra("reference").toString(),Gson().toJson(feedbackObject))
            Toast.makeText(this@UpdateAndDeleteFeedbackActivity
                ,"Feedback Update is Successfull",Toast.LENGTH_LONG).show();
        }
    }
}