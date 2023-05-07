package com.example.project1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BudgetActivity : AppCompatActivity() {

    private lateinit var transactions: ArrayList<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var balance: TextView
    private lateinit var budget: TextView
    private lateinit var expense: TextView
    private lateinit var addBtn : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)
        balance = findViewById(R.id.balance)
        budget = findViewById(R.id.budget)
        expense = findViewById(R.id.expense)
        recyclerView = findViewById(R.id.budget_recycleView)
        addBtn = findViewById(R.id.addBtn)

        transactions = arrayListOf(
            Transaction("Weekend Budget", 400.00),
            Transaction("Bananas", -4.00),
            Transaction("Gasoline", -40.90),
            Transaction("Breakfast", -9.99),
            Transaction("Water Bottles", -4.00),
            Transaction("Suncream", -4.00),
            Transaction("Car Park", -8.00)
        )

        transactionAdapter = TransactionAdapter(transactions)
        layoutManager = LinearLayoutManager(this)

        recyclerView.apply {
            adapter = transactionAdapter
            layoutManager = this@BudgetActivity.layoutManager
        }

        updateDashboard()

        addBtn.setOnClickListener {
            val intent = Intent(this,AddBudgetExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateDashboard(){
        val totalAmount: Double = transactions.map { it.amount }.sum()
        val budgetAmount: Double = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
        val expenseAmount: Double = totalAmount - budgetAmount
        balance.text = "$ %.2f".format(totalAmount)
        budget.text = "$ %.2f".format(budgetAmount)
        expense.text = "$ %.2f".format(expenseAmount)

    }
}
