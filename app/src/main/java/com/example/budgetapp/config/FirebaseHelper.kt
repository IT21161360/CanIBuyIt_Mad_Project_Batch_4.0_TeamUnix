package com.example.budgetapp.config

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseHelper {
    private val url:String = "https://budgetapp-c421a-default-rtdb.firebaseio.com"
    private val databaseEntity: FirebaseDatabase =
        Firebase.database(url)

    fun getDbInsertOrUpdateUrl(collection:String,entityReference:Long): String{
        return collection + "/" + entityReference
    }

    fun getData(reference: String): DatabaseReference {
        return this.databaseEntity.getReference(reference)
    }

    fun setData(reference: String, value: Boolean){
        val myRef = this.getData(reference)
        myRef.setValue(value)
    }

    fun setData(reference: String, value: Long){
        val myRef = this.getData(reference)
        myRef.setValue(value)
    }

    fun setData(reference: String, value: Double){
        val myRef = this.getData(reference)
        myRef.setValue(value)
    }

    fun setData(reference: String, value: Map<String,Any>){
        val myRef = this.getData(reference)
        myRef.setValue(value)
    }

    fun setData(reference: String, value: String){
        val myRef = this.getData(reference)
        myRef.setValue(value)
    }
}