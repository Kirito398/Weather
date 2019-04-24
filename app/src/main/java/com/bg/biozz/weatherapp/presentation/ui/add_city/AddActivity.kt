package com.bg.biozz.weatherapp.presentation.ui.add_city

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import com.bg.biozz.weatherapp.R
import com.bg.biozz.weatherapp.data.rest.APIClient
import com.bg.biozz.weatherapp.data.local.LocalDBHelper
import com.bg.biozz.weatherapp.data.repositories.MainRepositoryImpl
import com.bg.biozz.weatherapp.domain.interactors.MainInteractorImpl
import com.bg.biozz.weatherapp.domain.interfaces.add_city.AddCityInterface
import com.bg.biozz.weatherapp.presentation.presenters.add_city.AddCityPresenterImpl
import com.bg.biozz.weatherapp.presentation.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_add_city.*

class AddActivity : BaseActivity(0), AddCityInterface.View {
    private val TAG = "AddActivity"
    lateinit var mAddCityPresenter: AddCityPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        mAddCityPresenter = AddCityPresenterImpl(MainInteractorImpl(MainRepositoryImpl(APIClient().getClient(), LocalDBHelper(this))), this)

        Log.d(TAG, "onCreate")

        clickListeners()
    }

    private fun clickListeners(){
        agreed_btn.setOnClickListener {
            mAddCityPresenter.addNewCity(cityNameEdit.text.toString())
        }

        cancel_btn.setOnClickListener {
            finish()
            overridePendingTransition(0,0)
        }
    }

    override fun onAdded() {
        finish()
        overridePendingTransition(0,0)
    }

    override fun showIncorrectCityName(cityName: String) {
        Snackbar.make(addCityActivity, getString(R.string.incorrectCityName, cityName), Snackbar.LENGTH_LONG).show()
    }

    override fun onError(msg: String) {
        Snackbar.make(addCityActivity, getString(R.string.cityNotFound, msg), Snackbar.LENGTH_LONG).show()
    }
}
