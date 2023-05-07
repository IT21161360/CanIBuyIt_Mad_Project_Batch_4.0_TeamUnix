package com.example.project1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ItemLists : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<ItemList>
    private val nodeList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_lists)
        itemRecyclerView = findViewById(R.id.item_list)
        itemRecyclerView.layoutManager = LinearLayoutManager(this)
        itemRecyclerView.setHasFixedSize(true) // fixed size RecyclerView
        itemArrayList = arrayListOf<ItemList>()
        getItemData()
    }

    private fun getItemData() {
        db = FirebaseDatabase.getInstance().getReference("Items")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (itmsnapshot in snapshot.children) {
                        val item = itmsnapshot.getValue(ItemList::class.java)
                        itemArrayList.add(item!!)
                        nodeList.add(itmsnapshot.key.toString())
                    }
                    var adapter = itemAdapter(itemArrayList)
                    itemRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : itemAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val nodePath: String = nodeList[position]
                            val intent = Intent()
                            intent.putExtra("Item_id", nodePath)
                            setResult(2, intent)
                            finish() // finish activity
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle cancelled event
            }
        })
    }
}
