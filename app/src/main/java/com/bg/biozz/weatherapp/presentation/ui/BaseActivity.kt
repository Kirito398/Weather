package com.bg.biozz.weatherapp.presentation.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bg.biozz.weatherapp.presentation.ui.add_city.AddActivity
import com.bg.biozz.weatherapp.presentation.ui.main.MainActivity
import com.bg.biozz.weatherapp.R
import com.bg.biozz.weatherapp.presentation.ui.select_city.SelectActivity
import kotlinx.android.synthetic.main.bottom_navigation_view.*

abstract class BaseActivity(val navNumber: Int) : AppCompatActivity() {
    private val TAG = "BaseActivity"

    fun setupBottomNavigation(){
        bottom_navigation_view.setTextVisibility(false)
        bottom_navigation_view.enableItemShiftingMode(false)
        bottom_navigation_view.enableShiftingMode(false)
        bottom_navigation_view.enableAnimation(false)

        for(i in 0 until bottom_navigation_view.menu.size()){
            bottom_navigation_view.setIconTintList(i, null)
        }

        bottom_navigation_view.setOnNavigationItemSelectedListener {
            val nextActivity =
                    when(it.itemId){
                        R.id.nav_item_add -> AddActivity::class.java
                        R.id.nav_item_home -> MainActivity::class.java
                        R.id.nav_item_list -> SelectActivity::class.java
                        else -> {
                            Log.e(TAG, "Item error! $it")
                            null
                        }
                    }

            if(nextActivity != null){
                val intent = Intent(this, nextActivity)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(intent)
                overridePendingTransition(0,0)
                true
            }else{
                false
            }
        }
        bottom_navigation_view.menu.getItem(navNumber).isChecked = true
    }
}