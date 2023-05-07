package com.example.project1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.widget.addTextChangedListener
import com.example.project1.databinding.ActivityAddBudgetExpenseBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddBudgetExpenseActivity : AppCompatActivity() {
    private lateinit var labelInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var labelLayout: TextInputLayout
    private lateinit var amountLayout: TextInputLayout
    private lateinit var descriptionLayout: TextInputLayout
    private lateinit var closeButton: AppCompatImageButton
    private lateinit var db: DatabaseReference
    private lateinit var binding: ActivityAddBudgetExpenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBudgetExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        labelInput = binding.labelInput
        descriptionInput = binding.descriptionInput
        labelLayout = binding.labelLayout
        amountLayout = binding.amountLayout
        descriptionLayout = binding.descriptionLayout

        val amountInput = binding.amountInput
        val addTransactionButton = binding.addTransactionButton
        closeButton = binding.closeButton.findViewById(R.id.closeButton)

        labelInput.addTextChangedListener {
            if (it!!.count() > 0) {
                labelLayout.error = null
            }
        }
        amountInput.addTextChangedListener {
            if (it!!.count() > 0) {
                amountLayout.error = null
            }
        }

        descriptionInput.addTextChangedListener {
            if (it!!.count() > 0) {
                descriptionLayout.error = null
            }
        }

        addTransactionButton.setOnClickListener {
            val label: String = labelInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()
            val description: String = descriptionInput.text.toString()
            if (label.isEmpty()) {
                labelLayout.error = "Please Enter Valid Label"
            } else {
                labelLayout.error = null
            }
            if (amount == null) {
                amountLayout.error = "Please Enter Valid Amount"
            } else {
                amountLayout.error = null
            }
            if (description.isEmpty()) {
                descriptionLayout.error = "Please Enter Valid Description"
            } else {
                descriptionLayout.error = null
            }
            Toast.makeText(this, "Label: $label", Toast.LENGTH_SHORT).show()
        }

        closeButton.setOnClickListener {
            val intent = Intent(this, BudgetActivity::class.java)
            startActivity(intent)
        }
    }

    fun insert_Budget(view: View) {
        val label = binding.labelInput.text.toString()
        val amount = binding.amountInput.text.toString().toDoubleOrNull()
        val description = binding.descriptionInput.text.toString()
        db = FirebaseDatabase.getInstance().getReference("Budget")
        val transaction = Transaction(label, amount!!, description)
        val databaseReference = FirebaseDatabase.getInstance().reference
        val id = databaseReference.push().key
        db.child(id.toString()).setValue(transaction).addOnSuccessListener {
            binding.labelInput.editableText.clear()
            binding.amountInput.editableText.clear()
            binding.descriptionInput.editableText.clear()
            Toast.makeText(this, "Datainserted successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to insert data", Toast.LENGTH_SHORT).show()
        }
    }
}
