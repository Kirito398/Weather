package com.bg.biozz.weatherapp.presentation.ui.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bg.biozz.weatherapp.R
import com.bg.biozz.weatherapp.data.APIClient
import com.bg.biozz.weatherapp.data.LocalDBHelper
import com.bg.biozz.weatherapp.data.repositories.MainRepositoryImpl
import com.bg.biozz.weatherapp.data.utils.DrawableManager
import com.bg.biozz.weatherapp.domain.interactors.impl.MainInteractorImpl
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import com.bg.biozz.weatherapp.presentation.presenters.SelectCityPresenter
import com.bg.biozz.weatherapp.presentation.presenters.impl.SelectCityPresenterImpl
import com.bg.biozz.weatherapp.presentation.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_select_city.*
import kotlinx.android.synthetic.main.bottom_navigation_view.*

class SelectActivity : BaseActivity(2), SelectCityPresenter.Callback {
    private val TAG = "SelectActivity"
    private lateinit var mSelectCityPresenter: SelectCityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)

        setupBottomNavigation()
        Log.d(TAG, "onCreate")

        mSelectCityPresenter = SelectCityPresenterImpl(MainInteractorImpl(MainRepositoryImpl(APIClient().getClient(), LocalDBHelper(this))), this)

        prev_btn.setOnClickListener {
            finish()
            overridePendingTransition(0,0)
        }
    }

    override fun onResume() {
        super.onResume()

        bottom_navigation_view.menu.getItem(navNumber).isChecked = true
        error_txt.text = ""
        error_txt.visibility = View.GONE

        itemsLayout.removeAllViews()
        mSelectCityPresenter.loadCitiesDataList()
    }

    override fun addCityOnTheList(cityData: CityViewModel) {
        val view = layoutInflater.inflate(R.layout.item_layout, null)

        var text = view.findViewById<TextView>(R.id.cityName)
        text.text = cityData.cityName

        text = view.findViewById(R.id.weatherMain)
        text.text = cityData.weather

        text = view.findViewById(R.id.temp)
        text.text = cityData.tempMinMax

        val icon = view.findViewById<ImageView>(R.id.cityIcon)
        icon.setImageResource(DrawableManager().getIdDrawable(cityData.icon))

        view.setOnClickListener {
            Log.d(TAG, "Item clicked! - ${cityData.cityName}")
            mSelectCityPresenter.setDefaultCity(cityData.cityName)
            finish()
            overridePendingTransition(0,0)
        }

        itemsLayout.addView(view)
    }

    override fun showProgressBar(show: Boolean) {
        if(show){
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onError(msg: String) {
        val str = error_txt.text.toString() + msg + "\n"
        error_txt.text = str
        error_txt.visibility = View.VISIBLE
        Snackbar.make(mainView, msg, Snackbar.LENGTH_LONG)
    }
}
