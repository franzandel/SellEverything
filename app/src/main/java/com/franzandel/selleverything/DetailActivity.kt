package com.franzandel.selleverything

import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.franzandel.selleverything.extension.goTo
import com.franzandel.selleverything.extension.showStrikeThrough
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO: Pass Product bundle here
        val imageList = arrayListOf(
            "poster_alita", "poster_infinity_war", "poster_glass"
        )

        intro_pager.adapter = DetailAdapter(this, imageList)

        // To connect ViewPager2 with TabLayout
        TabLayoutMediator(intro_tab_layout, intro_pager) { _, _ -> }.attach()

        tvDetailOriginalPrice.showStrikeThrough()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> goTo(CartActivity::class.java)
        }
        return true
    }
}