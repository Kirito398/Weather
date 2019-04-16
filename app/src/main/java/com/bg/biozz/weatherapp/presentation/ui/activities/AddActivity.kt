package com.bg.biozz.weatherapp.presentation.ui.activities

import android.os.Bundle
import android.util.Log
import com.bg.biozz.weatherapp.R
import com.bg.biozz.weatherapp.presentation.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_add_city.*

class AddActivity : BaseActivity(0) {
    private val TAG = "AddActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        setupBottomNavigation()
        Log.d(TAG, "onCreate")

        prev_btn.setOnClickListener {
            finish()
        }
    }
}
