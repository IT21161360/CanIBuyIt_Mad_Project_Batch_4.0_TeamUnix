package com.example.project1


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class UserList : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var userAdapter: UserAdapter
    private lateinit var users: MutableList<UserModel>
    private lateinit var backtoUhome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        backtoUhome = findViewById(R.id.backToHomePage)

        users = mutableListOf()
        userAdapter = UserAdapter(users)
        recyclerView.adapter = userAdapter

        backtoUhome.setOnClickListener {
            val intent = Intent(this, AdminHome::class.java)
            startActivity(intent)
        }

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        dbRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                users.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val user = postSnapshot.getValue(UserModel::class.java)
                    if (user != null) {
                        users.add(user)
                    }
                }
                userAdapter.notifyDataSetChanged()
            }

            @SuppressLint("LongLogTag")
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("com.example.budgetmanagement.UserList", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }
}

//package com.example.budgetmanagement
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//class com.example.budgetmanagement.UserList : AppCompatActivity() {
//    private lateinit var dbRef: DatabaseReference
//    private lateinit var userAdapter: UserAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_user_list)
//        dbRef = FirebaseDatabase.getInstance().getReference("Users")
//
//    }
//}