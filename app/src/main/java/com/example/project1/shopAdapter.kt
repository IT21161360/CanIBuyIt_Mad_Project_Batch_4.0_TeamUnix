package com.example.project1
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView

class shopAdapter (private val shopList : ArrayList<ShopList>) : RecyclerView.Adapter<shopAdapter.ShopHolder>() {
    private lateinit var mListener: onShopClickListener
    interface onShopClickListener {
        fun onShopClick(position: Int)
    }
    fun setOnClickListener(listener: onShopClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopHolder {
        val shopView = LayoutInflater.from(parent.context).inflate(R.layout.shop, parent, false)
        return ShopHolder(shopView, mListener)
    }

    override fun onBindViewHolder(holder: ShopHolder, position: Int) {
        if (shopList.isNotEmpty()) {
            val currentShop = shopList[position]
            holder.shopName.setText(currentShop.shopName)
            holder.shopAddress.setText(currentShop.shopAddress)
            holder.shopPhoneNumber.setText(currentShop.shopPhoneNumber)
            holder.shopCity.setText(currentShop.shopCity)
            if (currentShop.shopImage != null) {
                val bytes = android.util.Base64.decode(currentShop.shopImage, android.util.Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                holder.shopImage.setImageBitmap(bitmap)
            } else {
                holder.shopImage.setImageBitmap(null)
            }
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopHolder(shopView: View, listener: onShopClickListener) : RecyclerView.ViewHolder(shopView) {
        val shopName: MaterialTextView = shopView.findViewById(R.id.edtName)
        val shopAddress:  MaterialTextView = shopView.findViewById(R.id.edtAddress)
        val shopPhoneNumber:  MaterialTextView = shopView.findViewById(R.id.edtPhoneNumber)
        val shopCity:  MaterialTextView = shopView.findViewById(R.id.edtCity)
        val shopImage : ImageView = shopView.findViewById(R.id.imageView2)

        init {
            shopView.setOnClickListener {
                listener.onShopClick(adapterPosition)
            }
        }
    }
}
