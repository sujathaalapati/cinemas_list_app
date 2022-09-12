package com.assesment.cinemaslist.data.network.responses

import com.google.gson.annotations.SerializedName
import com.assesment.cinemaslist.model.MetaData
import com.assesment.cinemaslist.data.network.MovieDto

data class CinemaListResponse(
    @SerializedName("data")
    val data: List<MovieDto>,

    @SerializedName("metadata")
    val metaData: MetaData
    )