package com.bg.biozz.weatherapp.presentation.ui.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import com.bg.biozz.weatherapp.R
import com.bg.biozz.weatherapp.data.APIClient
import com.bg.biozz.weatherapp.data.LocalDBHelper
import com.bg.biozz.weatherapp.data.repositories.MainRepositoryImpl
import com.bg.biozz.weatherapp.domain.interactors.impl.MainInteractorImpl
import com.bg.biozz.weatherapp.presentation.presenters.AddCityPresenter
import com.bg.biozz.weatherapp.presentation.presenters.impl.AddCityPresenterImpl
import com.bg.biozz.weatherapp.presentation.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_add_city.*

class AddActivity : BaseActivity(0), AddCityPresenter.Callback {
    private val TAG = "AddActivity"
    lateinit var mAddCityPresenter: AddCityPresenter

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

    override fun onError(msg: String) {
        Snackbar.make(addCityActivity, msg, Snackbar.LENGTH_LONG).show()
    }
}
