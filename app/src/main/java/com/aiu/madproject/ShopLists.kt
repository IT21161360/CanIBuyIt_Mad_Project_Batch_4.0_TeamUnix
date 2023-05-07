package com.aiu.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ShopLists : AppCompatActivity() {
    private lateinit var db:DatabaseReference
    private lateinit var shopRecyclerView: RecyclerView
    private lateinit var shopArrayList: ArrayList<ShopList>
    private val nodeList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_lists)
        shopRecyclerView = findViewById(R.id.shop_list)
        shopRecyclerView.layoutManager = LinearLayoutManager(this)
        shopRecyclerView.setHasFixedSize(true)
        shopArrayList = arrayListOf<ShopList>()
        getShopData()

    }

    private fun getShopData() {
        db = FirebaseDatabase.getInstance().getReference("Shops")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (shopsnapshot in snapshot.children) {
                        val shop = shopsnapshot.getValue(ShopList::class.java)
                        shopArrayList.add(shop!!)
                        nodeList.add(shopsnapshot.key.toString())
                    }
                    var adapter = shopAdapter(shopArrayList)
                    shopRecyclerView.adapter = adapter
                    adapter.setOnClickListener(object : shopAdapter.onShopClickListener{
                        override fun onShopClick(position: Int) {
                            val nodePath: String = nodeList[position]
                            val intent = Intent()
                            intent.putExtra("Shop_id", nodePath)
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