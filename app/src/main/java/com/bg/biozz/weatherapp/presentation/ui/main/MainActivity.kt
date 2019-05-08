package com.bg.biozz.weatherapp.presentation.ui.main

import android.app.ActionBar
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.bg.biozz.weatherapp.R
import com.bg.biozz.weatherapp.data.rest.APIClient
import com.bg.biozz.weatherapp.data.local.LocalRoomDB
import com.bg.biozz.weatherapp.data.repositories.MainRepositoryImpl
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.data.utils.DrawableManager
import com.bg.biozz.weatherapp.data.utils.NetworkChangeReceiver
import com.bg.biozz.weatherapp.domain.interactors.MainInteractorImpl
import com.bg.biozz.weatherapp.domain.interfaces.main.MainInterface
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import com.bg.biozz.weatherapp.domain.models.ForeCastViewModel
import com.bg.biozz.weatherapp.presentation.presenters.main.MainPresenterImpl
import com.bg.biozz.weatherapp.presentation.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_navigation_view.*

class MainActivity : BaseActivity(1), MainInterface.View, MainInterface.BroadCastReceiver {
    private val TAG = "MainActivity"
    lateinit var mMainPresenter: MainPresenterImpl
    private val receiver = NetworkChangeReceiver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigation()

        mMainPresenter = MainPresenterImpl(MainInteractorImpl(MainRepositoryImpl(APIClient().getClient(), LocalRoomDB.getClient(applicationContext))), this)
        Log.d(TAG, "onCreate")
    }

    override fun onResume(){
        super.onResume()
        bottom_navigation_view.menu.getItem(navNumber).isChecked = true
        registerReceiver(receiver, receiver.intentFilter())
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onInternetConnectionSuccess() {
        mMainPresenter.loadData(true)
    }

    override fun onInternetConnectionError() {
        Snackbar.make(daysView, getString(R.string.internetConnectionError), Snackbar.LENGTH_LONG).show()
        mMainPresenter.loadData(false)
    }

    override fun onLoadedCityData(cityViewModel: CityViewModel) {
        temp.text = cityViewModel.temp
        cityName.text = cityViewModel.cityName
        weather.text = cityViewModel.weather
        description.text = cityViewModel.description
        tempMinMax.text = getString(R.string.temp, cityViewModel.tempMinMax)
        windSpeed.text = getString(R.string.windSpeed, cityViewModel.windSpeed)
        humidity.text = getString(R.string.humidity, cityViewModel.humidity)
        pressure.text = getString(R.string.pressure, cityViewModel.pressure)
        icon.setImageResource(DrawableManager().getIdDrawable(cityViewModel.icon))
        lastUpdateTV.text = getString(R.string.lastUpdate, cityViewModel.dt)

        val anim = icon.drawable
        if(anim is Animatable){
            (anim as Animatable).start()
            Log.d(TAG, "Animate: ${(anim as Animatable).isRunning}")
        }
    }

    override fun onLoadedForeCast(foreCastViewModel: ForeCastViewModel) {
        daysView.removeAllViews()
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

            val anim = icon.drawable
            if(anim is Animatable){
                (anim as Animatable).start()
                Log.d(TAG, "Animate: ${(anim as Animatable).isRunning}")
            }

            daysView.addView(item)
        }
    }

    override fun showMainLoadingProgressBar(show: Boolean) {
        showProgressBar(mainProgressBar, show)
    }

    override fun showItemsLoadingProgressBar(show: Boolean) {
        showProgressBar(itemsProgressBar, show)
    }

    override fun showLastUpdateMessage(show: Boolean) {
        if(show){
            dataOutTV.visibility = View.VISIBLE
            lastUpdateTV.visibility = View.VISIBLE
        } else {
            dataOutTV.visibility = View.GONE
            lastUpdateTV.visibility = View.GONE
        }
    }

    override fun onLoadedError() {
        Snackbar.make(daysView, getString(R.string.loadingError), Snackbar.LENGTH_LONG).show()
    }

    private fun showProgressBar(bar: View, show: Boolean){
        if(show){
            mainScrollView.visibility = View.GONE
            bar.visibility = View.VISIBLE

            if(bar is ConstraintLayout){
                val anim = loadIcon.drawable
                if(anim is Animatable){
                    (anim as Animatable).start()
                    Log.d(TAG, "Animate: ${(anim as Animatable).isRunning}")
                }
            }
        } else {
            mainScrollView.visibility = View.VISIBLE
            bar.visibility = View.GONE
        }
    }
}
