package com.littlelemon.menu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.MenuCompat
import com.littlelemon.menu.ProductActivity.Companion.KEY_CATEGORY
import com.littlelemon.menu.ProductActivity.Companion.KEY_IMAGE
import com.littlelemon.menu.ProductActivity.Companion.KEY_PRICE
import com.littlelemon.menu.ProductActivity.Companion.KEY_TITLE
import com.littlelemon.menu.ProductsWarehouse.productsList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainActivity : ComponentActivity() {

    private val productsState: MutableStateFlow<Products> =
        MutableStateFlow(Products(productsList))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { InitUI() }
    }

    @Composable
    fun InitUI() {
        val products by productsState.collectAsState()
        ProductsGrid(products = products, startProductActivity = this::startProductActivity)
    }

    private fun startProductActivity(productItem: ProductItem) {
        val intent = Intent(this, ProductActivity::class.java).apply {
            putExtra(KEY_TITLE, productItem.title)
            putExtra(KEY_PRICE, productItem.price)
            putExtra(KEY_IMAGE, productItem.image)
            putExtra(KEY_CATEGORY, productItem.category)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.products_menu, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.groupId) {
            R.id.sorting -> {
                val type = when (item.itemId) {
                    R.id.sort_a_z -> SortType.Alphabetically
                    R.id.sort_price_asc -> SortType.PriceAsc
                    R.id.sort_price_desc -> SortType.PriceDesc
                    else -> SortType.Alphabetically
                }
                productsState.update {
                    Products(
                        SortHelper().sortProducts(
                            type,
                            productsList
                        )
                    )
                }
            }
            R.id.filter -> {
                val type = when (item.itemId) {
                    R.id.filter_all -> FilterType.All
                    R.id.filter_drinks -> FilterType.Drinks
                    R.id.filter_food -> FilterType.Food
                    R.id.filter_dessert -> FilterType.Dessert
                    else -> FilterType.All
                }
                productsState.update {
                    Products(
                        FilterHelper().filterProducts(
                            type,
                            productsList
                        )
                    )
                }
            }
        }
        return true
    }
}
