package com.example.project1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var etUserName: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var dbRef: DatabaseReference
    private lateinit var tTegister: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUserName = findViewById(R.id.AdminUserNameInput)
        etPassword = findViewById(R.id.adminPasswordInput)
        btnLogin = findViewById(R.id.adminLoginButton)
        tTegister = findViewById(R.id.adminSignUpNewText)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        btnLogin.setOnClickListener {
            login()
            println(dbRef)
        }

        tTegister.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private fun login(){
        val uName = etUserName.text.toString()
        val uPassword = etPassword.text.toString()
        dbRef.child(uName).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            if (it.value != null) {
                val valueMap = it.value as Map<String, Any>
                val upassword = valueMap["upassword"]
                val uemail = valueMap["uemail"]
                if (uemail == "gg@gmail.com" && upassword == uPassword){
                    Toast.makeText(this, "Log successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, AdminHome::class.java)
                    intent.putExtra("nameId", uName);
                    startActivity(intent)
                }else if (upassword == uPassword){
                    Toast.makeText(this, "Log successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, UserHome::class.java)
                    intent.putExtra("nameId", uName);
                    startActivity(intent)
                }
                println(upassword)
            } else {
                Log.i("firebase", "No value for this username")
            }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}