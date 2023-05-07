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

class UserProfile : AppCompatActivity() {

    private lateinit var etUserName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var btnUpdate: Button
    private lateinit var backtoUhome:TextView
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val nameId = intent.getStringExtra("nameId")
        etUserName = findViewById(R.id.userProfileUserNameInput)
        etEmail = findViewById(R.id.userProfileEmailInput)
        etAddress = findViewById(R.id.userProfileAddressInput)
        etPhoneNumber = findViewById(R.id.userProfilePhoneNumberInput)
        btnUpdate = findViewById(R.id.updateProfileButton)
        backtoUhome = findViewById(R.id.backToHomePage)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        if (nameId != null) {
            dbRef.child(nameId).get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                if (it.value != null) {
                    val valueMap = it.value as Map<String, Any>
                    val uname = valueMap["uname"]
                    val uemail = valueMap["uemail"]
                    val uaddress = valueMap["uaddress"]
                    val uphoneNumber = valueMap["uphoneNumber"]
                    etUserName.setText("Change the User Name: "+uname.toString())
                    etEmail.setText("Change the Email: "+uemail.toString())
                    etAddress.setText("Change the Address: "+uaddress.toString())
                    etPhoneNumber.setText("Change the Phone Number: "+uphoneNumber.toString())
                    println("upassword")
                    println(uname)
                } else {
                    Log.i("firebase", "No value for this username")
                }
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }

        btnUpdate.setOnClickListener{
            val uName = etUserName.text.toString()
            val uEmail = etEmail.text.toString()
            val uAddress = etAddress.text.toString()
            val uPhoneNumber = etPhoneNumber.text.toString()
            if (nameId != null) {
                upDateUserData(uName,uEmail,uAddress,uPhoneNumber,nameId)
            }
            val intent = Intent(this, UserHome::class.java)
            intent.putExtra("nameId", nameId);
            startActivity(intent)
        }

        backtoUhome.setOnClickListener {
            val intent = Intent(this, UserHome::class.java)
            intent.putExtra("nameId", nameId);
            startActivity(intent)
        }

    }

    private fun upDateUserData(
        uName: String,
        uEmail: String,
        uAddress: String,
        uPhoneNumber: String,
        nameId:String,
    ) {
        val user = mapOf<String,String>(
            "uname" to uName,
            "uemail" to uEmail,
            "uaddress" to uAddress,
            "uphoneNumber" to uPhoneNumber,
        )
        dbRef.child(nameId).updateChildren(user).addOnSuccessListener {

            Toast.makeText(this,"Successfuly Updated",Toast.LENGTH_SHORT).show()


        }.addOnFailureListener{

            Toast.makeText(this,"Failed to Update",Toast.LENGTH_SHORT).show()

        }
    }
}