package com.bg.biozz.weatherapp.presentation.presenters.select_city

import android.util.Log
import com.bg.biozz.weatherapp.domain.interfaces.main.MainInterface
import com.bg.biozz.weatherapp.domain.interfaces.select_city.SelectCityInterface
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class SelectCityPresenterImpl(private val mainInteractor: MainInterface.Interactor, private val selectCityView: SelectCityInterface.View) {
    private val TAG = "SelectCityPresenterImpl"

    fun loadCitiesDataListFromInternet() {
        val citiesList = mainInteractor.getDefaultCitiesList()

        selectCityView.cleanCityList()
        for (city in citiesList) {
            mainInteractor.getCityData(city)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                        onCityDataLoaded(result)
                    }, { error ->
                        onError(error, city)
                    })
        }
        selectCityView.showProgressBar(false)

        Log.d(TAG, "Load default city list from internet! ${citiesList.size}")
    }

    fun loadCitiesDataListFromLocalDB(){
        val citiesList = mainInteractor.getDefaultCitiesList()

        selectCityView.showProgressBar(true)
        selectCityView.cleanCityList()
        for (city in citiesList) {
            selectCityView.addCityOnTheList(mainInteractor.getCityDataFromLocalDB(city))
        }
        selectCityView.showProgressBar(false)

        Log.d(TAG, "Load default city list from local DB! ${citiesList.size}")
    }

    private fun onCityDataLoaded(cityData: CityData){
        val mCityViewModel = CityViewModel(
                cityData.name,
                cityData.weather[0].main,
                "${cityData.main.tempMin.toInt()} / ${cityData.main.tempMax.toInt()}",
                cityData.weather[0].icon,
                cityData.main.temp.toInt().toString(),
                cityData.main.pressure.toInt().toString(),
                cityData.main.humidity.toString(),
                cityData.weather[0].description,
                cityData.wind.speed.toInt().toString(),
                cityData.dt.toString()
        )

        mainInteractor.updateCityDataInLocalDB(mCityViewModel)
        Log.d(TAG, "CityData loaded! - ${cityData.name}")
        selectCityView.addCityOnTheList(mCityViewModel)
    }

    fun setDefaultCity(cityName: String) {
        mainInteractor.setDefaultCity(cityName)
    }

    private fun onError(t: Throwable, msg: String){
        Log.d(TAG, t.localizedMessage + ": " + msg)
        selectCityView.addCityOnTheList(mainInteractor.getCityDataFromLocalDB(msg))
        selectCityView.onError(msg)
    }
}