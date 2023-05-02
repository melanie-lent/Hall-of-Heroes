package com.example.hallofheroes

import retrofit2.Call
import retrofit2.http.GET
import com.example.hallofheroes.HeroResponse

interface SWapi {
    @GET("/people")
    fun fetchHeroes(): Call<HeroResponse>
}