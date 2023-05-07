package com.example.project1

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class UserHome : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var imgBackButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)
        val nameId = intent.getStringExtra("nameId")
        imgProfile = findViewById(R.id.homePageUserProfileIcon)
        imgBackButton =findViewById(R.id.userHomeLogoutButton)

        imgProfile.setOnClickListener{
            val intent = Intent(this, UserProfile::class.java)
            intent.putExtra("nameId", nameId);
            startActivity(intent)
        }

        imgBackButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}