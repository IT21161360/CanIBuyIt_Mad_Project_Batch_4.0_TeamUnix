package com.example.project1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class UserAdapter(private val users: List<UserModel>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        val tvUserEmail: TextView = itemView.findViewById(R.id.tvUserEmail)
        val tvPhoneNumber: TextView = itemView.findViewById(R.id.usersPhoneNumber)
        val tvAddress: TextView = itemView.findViewById(R.id.usersAddress)
        val userDeleteButton: ImageView = itemView.findViewById(R.id.userDeleteButton)
// Initialize other views
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.tvUserName.text = "User Name: " + user.uName
        holder.tvUserEmail.text = "User Email: " + user.uEmail
        holder.tvPhoneNumber.text = "PhoneNumber: " +user.uPhoneNumber
        holder.tvAddress.text = "Address: "+user.uAddress
        holder.userDeleteButton.setOnClickListener {
            showDeleteConfirmationDialog(holder.itemView.context, user)
        }
    }

    private fun showDeleteConfirmationDialog(context: Context, user: UserModel) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete User")
        builder.setMessage("Are you sure you want to delete this user?")
        builder.setPositiveButton("Yes") { _, _ ->
            deleteUser(user)
        }
        builder.setNegativeButton("No") { dialog, _ ->  dialog.dismiss()
        }
        builder.show()
    }

    private fun deleteUser(user: UserModel) {
        // Assuming that the user has a unique 'id' field in the UserModel class
        val userDbRef = FirebaseDatabase.getInstance().getReference("Users").child(user.uName.toString())
        userDbRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("UserAdapter", "User successfully deleted.")
            } else {
                Log.e("UserAdapter", "Error deleting user.", task.exception)
            }
        }
    }



    override fun getItemCount(): Int {
        return users.size
    }
}

