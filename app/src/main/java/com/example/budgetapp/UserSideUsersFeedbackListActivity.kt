package com.example.budgetapp

import CustomAdapter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.config.FirebaseHelper
import com.example.budgetapp.entity.Feedback
import com.example.budgetapp.entity.FeedbacksViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson


class UserSideUsersFeedbackListActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_user_side_users_feedback_list);

        val feedbacks = firebaseHelper.getData("feedbacks")
        val data = ArrayList<FeedbacksViewModel>()
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerview.layoutManager = LinearLayoutManager(this)
        feedbacks.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Data : ",snapshot.getValue().toString())

                val feedbackObjects = Gson().fromJson(snapshot.getValue().toString(), Array<Feedback>::class.java)
                data.clear()
                var count = 0;
                for(feedback in feedbackObjects){
                    if(feedback != null){
                        data.add(FeedbacksViewModel(feedback.getShopName(),feedback.getFeedback(),feedback.getRating(),count.toString()))
                    }
                    count++
                }

                val adapter = CustomAdapter(data)

                recyclerview.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException() // Never ignore errors
            }
        })
    }
}