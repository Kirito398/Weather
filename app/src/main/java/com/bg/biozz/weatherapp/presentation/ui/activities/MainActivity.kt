package com.bg.biozz.weatherapp.presentation.ui.activities

import android.app.ActionBar
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bg.biozz.weatherapp.R
import com.bg.biozz.weatherapp.data.APIClient
import com.bg.biozz.weatherapp.data.LocalDBHelper
import com.bg.biozz.weatherapp.data.repositories.MainRepositoryImpl
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.data.utils.DrawableManager
import com.bg.biozz.weatherapp.domain.interactors.impl.MainInteractorImpl
import com.bg.biozz.weatherapp.presentation.models.CityViewModel
import com.bg.biozz.weatherapp.presentation.models.ForeCastViewModel
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

        mMainPresenter = MainPresenterImpl(MainInteractorImpl(MainRepositoryImpl(APIClient().getClient(), LocalDBHelper(this))), this)

        mMainPresenter.getCityData(mMainPresenter.getDefaultCityList()[0])
        mMainPresenter.getForeCast(mMainPresenter.getDefaultCityList()[0])
    }

    override fun onResume(){
        super.onResume()
        bottom_navigation_view.menu.getItem(navNumber).isChecked = true
    }

    override fun onLoadedCityData(cityViewModel: CityViewModel) {
        temp.text = cityViewModel.temp
        cityName.text = cityViewModel.cityName
        weather.text = cityViewModel.weather
        description.text = cityViewModel.description
        tempMinMax.text = cityViewModel.tempMinMax
        windSpeed.text = cityViewModel.windSpeed
        humidity.text = cityViewModel.humidity
        pressure.text = cityViewModel.pressure
        icon.setImageResource(DrawableManager().getIdDrawable(cityViewModel.icon))
    }

    override fun onLoadedForeCast(foreCastViewModel: ForeCastViewModel) {
        for(i in 0 until foreCastViewModel.daysOfWeek.size) {
            val item = LinearLayout(this)
            item.layoutParams = LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
            item.orientation = LinearLayout.VERTICAL
            item.weightSum = ConstantUtils.ITEM_WEIGHT

            val dayOfWeek = TextView(this)
            dayOfWeek.text = foreCastViewModel.daysOfWeek[i]
            dayOfWeek.layoutParams = LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
            dayOfWeek.gravity = Gravity.CENTER_HORIZONTAL
            item.addView(dayOfWeek)

            val icon = ImageView(this)
            icon.layoutParams = LinearLayout.LayoutParams(DrawableManager().convertDPtoPX(ConstantUtils.ICON_SIZE, this), DrawableManager().convertDPtoPX(ConstantUtils.ICON_SIZE, this))
            icon.setImageResource(DrawableManager().getIdDrawable(foreCastViewModel.icon[i]))
            item.addView(icon)

            val temp = TextView(this)
            temp.text = foreCastViewModel.temp[i]
            temp.layoutParams = LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
            temp.gravity = Gravity.CENTER_HORIZONTAL
            item.addView(temp)

            daysView.addView(item)
        }
    }

    override fun onLoadedError(msg: String) {
        Snackbar.make(daysView, msg, Snackbar.LENGTH_LONG).show()
    }
}
