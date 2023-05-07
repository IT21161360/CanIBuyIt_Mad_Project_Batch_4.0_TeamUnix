package com.example.project1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {
    private lateinit var etUserName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etPassword: EditText
    private lateinit var etRePassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tTextLogin: TextView
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        etUserName = findViewById(R.id.RegisterUserNameInput)
        etEmail = findViewById(R.id.RegisterEmailInput)
        etAddress = findViewById(R.id.RegisterAddressInput)
        etPhoneNumber = findViewById(R.id.RegisterPhoneNumberInput)
        etPassword = findViewById(R.id.RegisterPasswordInput)
        etRePassword = findViewById(R.id.RegisterReEnter_PasswordInput)
        btnRegister = findViewById(R.id.UserRegisterButton)
        tTextLogin = findViewById(R.id.alreadyHaveAccountText)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        btnRegister.setOnClickListener {
            saveUserData()
            println(dbRef)
        }

        tTextLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun saveUserData() {
        val uName = etUserName.text.toString()
        val uEmail = etEmail.text.toString()
        val uAddress = etAddress.text.toString()
        val uPhoneNumber = etPhoneNumber.text.toString()
        val uPassword = etPassword.text.toString()
        val uRePassword = etRePassword.text.toString()
        val uId = dbRef.push().key!!
        val user = UserModel(uId, uName,uEmail,uAddress,uPhoneNumber,uPassword)
        dbRef.child(uName).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                etUserName.text.clear()
                etPassword.text.clear()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}