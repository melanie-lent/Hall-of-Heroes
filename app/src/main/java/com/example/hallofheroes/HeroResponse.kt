package com.example.hallofheroes

import com.google.gson.annotations.SerializedName

class HeroResponse {
    @SerializedName("results")
    lateinit var heroItems: List<Hero>
}