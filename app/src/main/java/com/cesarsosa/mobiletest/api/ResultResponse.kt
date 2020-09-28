package com.cesarsosa.mobiletest.api


import com.google.gson.annotations.SerializedName

data class ResultsResponse<T>(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("data")
    val data: ResultDataResponse<T>? = null
)