package com.jihun.diffutilexample.data

import com.google.gson.annotations.SerializedName

data class ResponseSampleDataInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("country") val country: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("coord") val coord: Coord?,
) {
    data class Coord(
        @SerializedName("lat") val lat: Double,
        @SerializedName("lon") val lon: Double,
    )
}