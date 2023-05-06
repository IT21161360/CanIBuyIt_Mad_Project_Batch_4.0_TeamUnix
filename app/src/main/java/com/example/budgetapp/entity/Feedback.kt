package com.example.budgetapp.entity

class Feedback {
    private var feedback:String = ""
    private var rating:Float = 0F
    private var shopName:String = ""

    fun setFeedback(feedback:String){
        this.feedback = feedback
    }

    fun getFeedback():String{
        return this.feedback;
    }

    fun setRating(rating:Float){
        this.rating = rating
    }

    fun getRating():Float{
        return this.rating;
    }

    fun setShopName(shopName:String){
        this.shopName = shopName
    }

    fun getShopName():String{
        return this.shopName;
    }
}