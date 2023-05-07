package com.example.project1
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.project1.databinding.ActivityItemBinding
import com.example.project1.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.io.InputStream

class ItemActivity : AppCompatActivity() {
    var sImage: String? = null
    var nodeId =""
    private lateinit var db: DatabaseReference
    private lateinit var binding: ActivityItemBinding

    private lateinit var launcher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    try {
                        val inputStream: InputStream? = contentResolver.openInputStream(uri)
                        if (inputStream != null) {
                            val myBitmap = BitmapFactory.decodeStream(inputStream)
                            val stream = ByteArrayOutputStream()
                            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                            val bytes = stream.toByteArray()
                            sImage = Base64.encodeToString(bytes, Base64.DEFAULT)
                            binding.imageView.setImageBitmap(myBitmap)
                            inputStream.close()
                        }
                    } catch (ex: Exception) {
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
                nodeId = intent.getStringExtra("Item_id").toString()
            }
            db = FirebaseDatabase.getInstance().getReference("Items")
            db.child(nodeId).get().addOnSuccessListener {
                if (it.exists()) {
                    binding.nameEnter.setText(it.child("itemName").value.toString())
                    binding.priceEnter.setText(it.child("itemPrice").value.toString())
                    binding.enterAvailable.setText(it.child("itemAvailability").value.toString())
                    sImage = it.child("itemImage").value.toString()
                    val bytes = Base64.decode(sImage, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    binding.imageView.setImageBitmap(bitmap)
                    binding.button4.visibility = View.VISIBLE
                    binding.button5.visibility = View.VISIBLE
                    binding.button2.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(this, "Item Not Found", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


    //insert_item_data
    fun insert_data(view: View) {
        val itemName = binding.nameEnter.text.toString()
        val itemPrice = binding.priceEnter.text.toString()
        val itemAvailability = binding.enterAvailable.text.toString()
        db = FirebaseDatabase.getInstance().getReference("Items")
        val item = ItemList(itemName, itemPrice, itemAvailability, sImage)
        val databaseReference = FirebaseDatabase.getInstance().reference
        val id = databaseReference.push().key
        db.child(id.toString()).setValue(item).addOnSuccessListener {
            binding.nameEnter.editableText.clear()
            binding.priceEnter.editableText.clear()
            sImage = null
            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to insert data", Toast.LENGTH_SHORT).show()
        }
    }

    //insert_item_image
    fun insert_image(view: View) {
        val myfileintent = Intent(Intent.ACTION_GET_CONTENT)
        myfileintent.type = "image/*"
        launcher.launch(myfileintent)
    }

    //show items list
    fun showList(view: View) {
        var i:Intent
        i = Intent(this,ItemLists::class.java)
        itemResultLauncher.launch(i)
    }


    fun editData(view: View) {
        val itemName = binding.nameEnter.editableText.toString()
        val itemPrice = binding.priceEnter.editableText.toString()
        val itemAvailability = binding.enterAvailable.editableText.toString()
        db = FirebaseDatabase.getInstance().getReference("Items")
        val item = ItemList(itemName, itemPrice, itemAvailability, sImage)
        db.child(nodeId).setValue(item).addOnSuccessListener {
            binding.nameEnter.editableText.clear()
            binding.priceEnter.editableText.clear()
            sImage = null
            binding.button4.visibility = View.INVISIBLE
            binding.button5.visibility = View.INVISIBLE
            binding.button2.visibility = View.VISIBLE
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Data Not Inserted", Toast.LENGTH_SHORT).show()
        }
    }

    //delete_item
    fun deleteData(view: View) {
        db = FirebaseDatabase.getInstance().getReference("Items")
        db.child(nodeId).removeValue()

    }
}
