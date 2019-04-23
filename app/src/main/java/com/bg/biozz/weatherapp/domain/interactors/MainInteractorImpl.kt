package com.bg.biozz.weatherapp.domain.interactors

import android.util.Log
import com.bg.biozz.weatherapp.domain.interfaces.main.MainInterface
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.ForeCast
import io.reactivex.Single

class MainInteractorImpl(private val mainRepository: MainInterface.Repository) : MainInterface.Interactor {
    private val TAG = "MainInteractorImpl"

    override fun getCityData(cityName: String): Single<CityData> {
        return mainRepository.getCityData(cityName)
    }

    override fun getForeCast(cityName: String): Single<ForeCast> {
        return mainRepository.getForeCast(cityName)
    }

    override fun getDefaultCity(): String {
        return mainRepository.getDefaultCity()
    }


    override fun setDefaultCity(cityName: String) {
        mainRepository.setDefaultCity(cityName)
    }

    override fun getDefaultCitiesList(): List<String> {
        return mainRepository.getCitiesList()
    }

    override fun addNewCity(cityName: String) {
        if(!isAdded(cityName)){
            Log.d(TAG, "City $cityName added!")
            mainRepository.addCityIntoDB(cityName)
        }else {
            Log.d(TAG, "City $cityName has been already added!")
        }
        Log.d(TAG, "Set default city: $cityName")
        mainRepository.setDefaultCity(cityName)
    }

    private fun isAdded(cityName: String): Boolean{
        val citiesList = mainRepository.getCitiesList()

        for(str in citiesList){
            if(str == cityName){
                return true
            }
        }
        return false
    }
}