package com.example.trueclub.models


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("probability")
    val probability: Double
)