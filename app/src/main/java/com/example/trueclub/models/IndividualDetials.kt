package com.example.trueclub.models


import com.google.gson.annotations.SerializedName

data class IndividualDetials(
    @SerializedName("country")
    val country: List<Country>,
    @SerializedName("name")
    val name: String
)