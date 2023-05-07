package com.example.budgetapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        setContentView(R.layout.activity_main)

        val btnAddFeedback = findViewById<Button>(R.id.btnAddFeedback)
        val btnListFeedback = findViewById<Button>(R.id.btnListFeedback)

        btnAddFeedback.setOnClickListener {
            val intent = Intent(this,FeedbackActivity::class.java)
            startActivity(intent);
        }

        btnListFeedback.setOnClickListener {
            val intent = Intent(this,UserSideUsersFeedbackListActivity::class.java)
            startActivity(intent)
        }
    }
}