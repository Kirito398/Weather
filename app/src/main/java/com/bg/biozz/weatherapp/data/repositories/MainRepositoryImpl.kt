package com.bg.biozz.weatherapp.data.repositories

import android.util.Log
import com.bg.biozz.weatherapp.BuildConfig
import com.bg.biozz.weatherapp.data.rest.API
import com.bg.biozz.weatherapp.data.local.LocalRoomDB
import com.bg.biozz.weatherapp.data.utils.ConstantUtils
import com.bg.biozz.weatherapp.domain.interfaces.main.MainInterface
import com.bg.biozz.weatherapp.domain.models.CityData
import com.bg.biozz.weatherapp.domain.models.CityViewModel
import com.bg.biozz.weatherapp.domain.models.ForeCast
import com.bg.biozz.weatherapp.domain.models.ForeCastViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainRepositoryImpl(private val webClient: API, private val localRoomClient: LocalRoomDB) : MainInterface.Repository {
    private val TAG = "MainRepositoryImpl"

    override fun getCityData(cityName: String): Single<CityData> {
        return webClient
                .messages(cityName, BuildConfig.KEY, ConstantUtils.METRIC)
                .subscribeOn(Schedulers.io())
    }

    override fun getForeCast(cityName: String): Single<ForeCast> {
        return webClient
                .forecast(cityName, BuildConfig.KEY, ConstantUtils.METRIC)
                .subscribeOn(Schedulers.io())
    }

    override fun getCityDataFromLocalDB(cityName: String): Single<CityViewModel> {
        return localRoomClient
                .cityDataDao()
                .getCityData(cityName)
                .subscribeOn(Schedulers.io())
    }

    override fun getForeCastFromLocalDB(cityName: String): Single<ForeCastViewModel> {
        return localRoomClient
                .foreCastDao()
                .getForeCast(cityName)
                .subscribeOn(Schedulers.io())
    }

    override fun getCitiesList(): Single<List<CityViewModel>> {
        return localRoomClient
                .cityDataDao()
                .getCitiesList()
                .subscribeOn(Schedulers.io())
    }

    override fun addCityIntoDB(cityViewModel: CityViewModel) : Single<Unit> {
        return Single.fromCallable{
            localRoomClient.cityDataDao().insertCityData(cityViewModel)
        }
                .subscribeOn(Schedulers.io())
    }

    override fun insertNAForeCastInLocalDB(cityName: String) {
        val daysOfWeek = mutableListOf<String>()
        val icons = mutableListOf<String>()
        val temps = mutableListOf<String>()

        for(i in 1..5) {
            daysOfWeek.add(ConstantUtils.NA)
            icons.add(ConstantUtils.NA)
            temps.add(ConstantUtils.NA)
        }

        val mForeCast = ForeCastViewModel(
                cityName,
                daysOfWeek,
                icons,
                temps
        )

        var d: Disposable? = null
        d = Single.fromCallable{
            localRoomClient.foreCastDao().insertForeCast(mForeCast)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess(d, "ForeCast for city $cityName added!")
                },{
                    onError(d, "Error in added ForeCast for city $cityName!")
                })
    }


    override fun setDefaultCity(cityName: String): Single<Unit> {
        return Single.fromCallable {
            localRoomClient.cityDataDao()
                    .setDefaultCity(cityName)
        }
                .subscribeOn(Schedulers.io())
    }

    override fun clearDefaultCity(cityName: String): Single<Unit> {
        return Single.fromCallable { localRoomClient.cityDataDao()
                .clearDefaultCity(cityName)
        }
                .subscribeOn(Schedulers.io())
    }

    override fun updateCityDataInLocalDB(cityViewModel: CityViewModel) {
        cityViewModel.setIsDefault(1)
        var d: Disposable? = null
        d = Single.fromCallable{
            localRoomClient.cityDataDao().updateCityData(cityViewModel)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess(d, "City data updated!")
                },{
                    error -> onError(d, error.localizedMessage)
                })
    }

    override fun updateForeCastInLocalDB(foreCastViewModel: ForeCastViewModel) {
        var d: Disposable? = null
        d = Single.fromCallable{
            localRoomClient.foreCastDao().updateForeCast(foreCastViewModel)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess(d, "ForeCast updated!")
                },{
                    error -> onError(d, error.localizedMessage)
                })
    }

    override fun getDefaultCity(): Single<CityViewModel> {
        return localRoomClient
                .cityDataDao()
                .getDefaultCity()
                .subscribeOn(Schedulers.io())
    }

    private fun onSuccess(d: Disposable?, msg: String){
        Log.d(TAG, msg)
        d?.dispose()
    }

    private fun onError(d: Disposable?, msg: String){
        Log.d(TAG, msg)
        d?.dispose()
    }
}