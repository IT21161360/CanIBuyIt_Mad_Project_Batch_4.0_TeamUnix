package com.aiu.madproject

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.aiu.madproject.databinding.ActivityShopBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.io.InputStream

class shopActivity : AppCompatActivity() {
    var sImage: String? = null
    var nodeId=""
    private lateinit var db : DatabaseReference
    private lateinit var binding: ActivityShopBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result:ActivityResult->
            if(result.resultCode== RESULT_OK){
                val uri = result.data?.data
                if(uri!=null){
                    try{
                        val inputStream : InputStream?= contentResolver.openInputStream(uri)
                        if(inputStream!=null){
                            val myBitmap = BitmapFactory.decodeStream(inputStream)
                            val stream = ByteArrayOutputStream()
                            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                            val bytes = stream.toByteArray()
                            sImage = Base64.encodeToString(bytes, Base64.DEFAULT)
                            binding.imageView.setImageBitmap(myBitmap)
                            inputStream.close()
                        }
                    }catch(ex:Exception){
                        Toast.makeText(this, ex.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private val itemResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == 2) {
            val intent = result.data
            if (intent != null) {
                nodeId = intent.getStringExtra("Shop_id").toString()
            }
            db = FirebaseDatabase.getInstance().getReference("Shops")
            db.child(nodeId).get().addOnSuccessListener {
                if (it.exists()) {
                    binding.shopNameEnter.setText(it.child("shopName").value.toString())
                    binding.shopAddressEnter.setText(it.child("shopAddress").value.toString())
                    binding.shopPhoneNumberEnter.setText(it.child("shopPhoneNumber").value.toString())
                    binding.shopCityEnter.setText(it.child("shopCity").value.toString())
                    sImage = it.child("shopImage").value.toString()
                    val bytes = Base64.decode(sImage, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    binding.imageView.setImageBitmap(bitmap)
                    binding.editShopButton.visibility = View.VISIBLE
                    binding.deleteShopButton.visibility = View.VISIBLE
                    binding.addShopButton.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(this, "Shop Not Found", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun insert_data(view: View) {
        val shopName = binding.shopNameEnter.text.toString()
        val shopAddress = binding.shopAddressEnter.text.toString()
        val shopPhoneNumber = binding.shopPhoneNumberEnter.text.toString()
        val shopCity = binding.shopCityEnter.text.toString()
        db = FirebaseDatabase.getInstance().getReference("Shops")
        val shop = ShopList(shopName,shopAddress,shopPhoneNumber,shopCity,sImage)
        val databaseReference = FirebaseDatabase.getInstance().reference
        val id = databaseReference.push().key
        db.child(id.toString()).setValue(shop).addOnSuccessListener {
            binding.shopNameEnter.editableText.clear()
            binding.shopAddressEnter.editableText.clear()
            binding.shopPhoneNumberEnter.editableText.clear()
            binding.shopCityEnter.editableText.clear()
            sImage = null
            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to insert data", Toast.LENGTH_SHORT).show()
        }


    }

    fun insert_image(view: View) {
        val myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.type = "image/*"
        launcher.launch(myfileintent)
    }
    fun showList(view: View) {
        var i:Intent
        i = Intent(this,ShopLists::class.java)
        itemResultLauncher.launch(i)
    }
    fun editData(view: View) {
        val shopName = binding.shopNameEnter.text.toString()
        val shopAddress = binding.shopAddressEnter.text.toString()
        val shopPhoneNumber = binding.shopPhoneNumberEnter.text.toString()
        val shopCity = binding.shopCityEnter.text.toString()
        db = FirebaseDatabase.getInstance().getReference("Shops")
        val shop = ShopList(shopName, shopAddress, shopPhoneNumber, shopCity , sImage)
        db.child(nodeId).setValue(shop).addOnSuccessListener {
            binding.shopNameEnter.editableText.clear()
            binding.shopAddressEnter.editableText.clear()
            binding.shopPhoneNumberEnter.editableText.clear()
            binding.shopCityEnter.editableText.clear()
            sImage = null
            binding.editShopButton.visibility = View.INVISIBLE
            binding.deleteShopButton.visibility = View.INVISIBLE
            binding.addShopButton.visibility = View.VISIBLE
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_SHORT).show()
        }

    }
    fun deleteData(view: View) {
        db = FirebaseDatabase.getInstance().getReference("Shops")
        db.child(nodeId).removeValue().addOnSuccessListener {
            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, shopActivity::class.java)
            startActivity(intent)

        }.
        addOnFailureListener {
            Toast.makeText(this, "Data Not Deleted", Toast.LENGTH_SHORT).show()

        }
    }
}