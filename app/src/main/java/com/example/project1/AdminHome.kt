package com.example.project1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
class AdminHome : AppCompatActivity() {
    private lateinit var btnUpdate: Button
    private lateinit var imgBackButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        val nameId = intent.getStringExtra("nameId")
        imgBackButton =findViewById(R.id.userHomeLogoutButton)
        btnUpdate = findViewById(R.id.viewAllUsersButton)

        imgBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnUpdate.setOnClickListener {
            val intent = Intent(this, UserList::class.java)
            startActivity(intent)
        }



    }
}