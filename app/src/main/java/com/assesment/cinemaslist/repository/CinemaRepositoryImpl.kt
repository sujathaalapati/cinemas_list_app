package com.assesment.cinemaslist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.assesment.cinemaslist.data.network.Api
import com.assesment.cinemaslist.data.network.toMovieDetails
import com.assesment.cinemaslist.model.Cinema
import com.assesment.cinemaslist.model.CinemaDetails
import com.assesment.cinemaslist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class CinemaRepositoryImpl
@Inject
constructor(
    private val api: Api
    ): CinemaRepository{

    override fun getAllMovies(): Flow<PagingData<Cinema>> = Pager(
        PagingConfig(1)
    ) {
        CinemaPagingSource(api)
    }.flow

    /*override fun getMoviesByPage(page: Int): Flow<PagingData<Movie>> = Pager(
        PagingConfig(page)
    ) {
        MoviePagingSource(api)
    }.flow*/

    override fun getMovieDetails(movieId: Int): Flow<Resource<CinemaDetails>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.getMovieDetails(movieId).toMovieDetails()))

        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

}














