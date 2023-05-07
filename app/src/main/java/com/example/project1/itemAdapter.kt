package com.example.project1

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView

class itemAdapter(private val itemList: ArrayList<ItemList>) : RecyclerView.Adapter<itemAdapter.ItemHolder>() {

    private lateinit var mListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener:onItemClickListener){
        mListener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemHolder(itemView,mListener)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (itemList.isNotEmpty()) {
            val currentItem = itemList[position]
            holder.itemName.text = currentItem.itemName.toString()
            holder.itemPrice.text = currentItem.itemPrice.toString()
            holder.itemAvailability.text = currentItem.itemAvailability.toString()
            if(currentItem.itemImage!=null) {
                val bytes =
                    android.util.Base64.decode(currentItem.itemImage, android.util.Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                holder.itemImage.setImageBitmap(bitmap)

            }else{
                holder.itemImage.setImageBitmap(null)
            }
        }
    }

    class ItemHolder(itemView: View , listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val itemName: MaterialTextView = itemView.findViewById(R.id.edtName)
        val itemPrice: MaterialTextView = itemView.findViewById(R.id.edtPrice)
        val itemAvailability: MaterialTextView = itemView.findViewById(R.id.edtAvailability)
        val itemImage: ImageView = itemView.findViewById(R.id.imageView2)
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)

            }
        }
    }
}
