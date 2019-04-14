package com.bg.biozz.weatherapp.presentation.ui.activities

import android.os.Bundle
import android.util.Log
import com.bg.biozz.weatherapp.R
import com.bg.biozz.weatherapp.presentation.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_select_city.*

class SelectActivity : BaseActivity(2) {
    private val TAG = "SelectActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)

        setupBottomNavigation()
        Log.d(TAG, "onCreate")

        prev_btn.setOnClickListener {
            finish()
        }
    }
}
