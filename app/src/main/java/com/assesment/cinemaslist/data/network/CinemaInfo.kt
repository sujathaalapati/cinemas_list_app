package com.assesment.cinemaslist.data.network

import com.google.gson.annotations.SerializedName
import com.assesment.cinemaslist.model.Cinema
import com.assesment.cinemaslist.model.CinemaDetails

class MovieDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("poster")
    val poster: String,

    @SerializedName("year")
    val year: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("imdb_rating")
    val rating: String,

    @SerializedName("rated")
    val rated: String,

    @SerializedName("released")
    val released: String,

    @SerializedName("runtime")
    val runtime: String,

    @SerializedName("director")
    val director: String,

    @SerializedName("writer")
    val writer: String,

    @SerializedName("actors")
    val actors: String,

    @SerializedName("plot")
    val plot: String,

    @SerializedName("awards")
    val awards: String,

    @SerializedName("imdb_votes")
    val votes: String,

    @SerializedName("genres")
    val genres: List<String>? = null,

    @SerializedName("images")
    val images: List<String>? = null
)

    fun MovieDto.toMovie(): Cinema {
        return Cinema(
            id = id,
            title = title,
            poster = poster,
            year = year,
            country = country,
            rating = rating,
            genres = genres,
            images = images
        )
    }

    fun MovieDto.toMovieDetails(): CinemaDetails {
        return CinemaDetails(
            id = id,
            title = title,
            poster = poster,
            year = year,
            country = country,
            rating = rating,
            rated = rated,
            released = released,
            runtime = runtime,
            director = director,
            writer = writer,
            actors = actors,
            plot = plot,
            awards = awards,
            votes = votes,
            genres = genres,
            images = images
        )
    }







