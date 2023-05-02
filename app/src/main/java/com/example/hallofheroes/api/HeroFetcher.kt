package com.example.hallofheroes.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hallofheroes.Hero
import com.example.hallofheroes.HeroResponse
import com.example.hallofheroes.SWapi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "HeroFetcher"

class HeroFetcher {
    private val swapi: SWapi
    private var apiUrl: String = "https://swapiclone.herokuapp.com/"

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        swapi = retrofit.create(SWapi::class.java)
    }


    fun fetchHeroes(livedata: MutableLiveData<List<Hero>>) {
        //val responseLiveData: MutableLiveData<List<Hero>> = MutableLiveData()
        val heroRequest: Call<HeroResponse> = swapi.fetchHeroes()

        heroRequest.enqueue(object : Callback<HeroResponse> {
            override fun onFailure(call: Call<HeroResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch people", t)
            }

            override fun onResponse(call: Call<HeroResponse>,
                                    response: Response<HeroResponse>) {
                val heroResponse: HeroResponse? = response.body()
                var heroItems: List<Hero> = heroResponse?.heroItems
                    ?: mutableListOf()
                Log.d(TAG, "response received:  $heroItems")
                livedata.value = heroItems
            }
        })
    }
}