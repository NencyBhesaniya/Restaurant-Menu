package com.littlelemon.menu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class ProductActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productItem =
            ProductItem("", 0.0, "", -1)//todo replace with the passed values from intent
        setContent { ProductDetails(productItem=productItem) }
    }
    companion object {
        const val KEY_TITLE = "title"
        const val KEY_PRICE = "price"
        const val KEY_IMAGE = "image"
        const val KEY_CATEGORY = "category"
    }
    val title=intent.getStringExtra(KEY_TITLE) ?:""
    val price=intent.getDoubleExtra(KEY_PRICE,0.0)
    val category=intent.getStringExtra(KEY_CATEGORY) ?:""
    val image=intent.getIntExtra(KEY_IMAGE,-1)
    val productItem=ProductItem(title=title,price=price,category=category,image=image)
}