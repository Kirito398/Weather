package com.bg.biozz.weatherapp.presentation.ui.activities

import android.os.Bundle
import android.util.Log
import com.bg.biozz.weatherapp.R
import com.bg.biozz.weatherapp.domain.models.ForeCastData
import com.bg.biozz.weatherapp.presentation.models.CityViewModel
import com.bg.biozz.weatherapp.presentation.presenters.MainPresenter
import com.bg.biozz.weatherapp.presentation.presenters.impl.MainPresenterImpl
import com.bg.biozz.weatherapp.presentation.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_navigation_view.*

class MainActivity : BaseActivity(1), MainPresenter.Callback {
    private val TAG = "MainActivity"
    lateinit var mMainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigation()
        Log.d(TAG, "onCreate")

        mMainPresenter = MainPresenterImpl(this)
        mMainPresenter.getCityData()
    }

    override fun onLoadedCityData(cityViewModel: CityViewModel) {
        temp.text = cityViewModel.temp
        cityName.text = cityViewModel.cityName
        weather.text = cityViewModel.weather
    }

    override fun onLoadedForeCast(foreCastData: ForeCastData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume(){
        super.onResume()
        bottom_navigation_view.menu.getItem(navNumber).isChecked = true
    }

    override fun onError(msg: String) {
        cityName.setText(msg)
    }
}
