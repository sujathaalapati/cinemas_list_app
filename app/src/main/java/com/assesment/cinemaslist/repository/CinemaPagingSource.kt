package com.assesment.cinemaslist.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.PagingSource.LoadResult.Page
import com.assesment.cinemaslist.data.network.Api
import com.assesment.cinemaslist.data.network.toMovie
import com.assesment.cinemaslist.model.Cinema
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CinemaPagingSource
@Inject
constructor(
    private val api: Api
    ) : PagingSource<Int, Cinema>(){

    override fun getRefreshKey(state: PagingState<Int, Cinema>): Int? {
        return state.anchorPosition?.let {
            //state.closestPageToPosition(it)?.prevKey
                anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cinema> {
        return try {
            //val page = if (params is Append) params.key else null
            val page = params.key ?: 1

            val response = api.getAllMovies(page).data.map { it.toMovie() }
            Page (
                data = response,
                prevKey = null,
                nextKey = page + 1
            )
        } catch (e : IOException) {
            LoadResult.Error(e)
        } catch (e : HttpException) {
            LoadResult.Error(e)
        }
    }


}