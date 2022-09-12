package com.assesment.cinemaslist.ui.cinema.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.assesment.cinemaslist.MainCoroutinesRule
import com.assesment.cinemaslist.getOrAwaitValueTest
import com.assesment.cinemaslist.model.CinemaDetails
import com.assesment.cinemaslist.repository.CinemaRepository
import com.assesment.cinemaslist.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CinemaDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()

    private lateinit var viewModel: CinemaDetailsViewModel

    @Mock
    lateinit var cinemaDetails: CinemaDetails

    @Mock
    lateinit var repository: CinemaRepository

    @Mock
    lateinit var cinemaDetailsResponseObserver: Observer<Resource<CinemaDetails>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = CinemaDetailsViewModel(repository)
    }

    @Test
    fun `when fetching results ok then return a movie details successfully`() {
        runTest {
            val flow = flow {
                emit(Resource.loading(null))
                emit(Resource.success(cinemaDetails))
            }
            val res = Resource.success(cinemaDetails)
            viewModel.cinemaDetails.observeForever(cinemaDetailsResponseObserver)
            given(repository.getMovieDetails(anyInt())).willReturn(flow)
            //verify(repository).getMovieDetails(anyInt())
            viewModel.setMovieId(anyInt())
            assertNotNull(viewModel.cinemaDetails.value)
            assertEquals(res,viewModel.cinemaDetails.getOrAwaitValueTest())
        }
    }

    @Test
    fun `when fetching results fails then return error`() {

        runTest {
            val flow = flow {
                emit(Resource.error("Check Network Connection!",null))
            }
            viewModel.cinemaDetails.observeForever(cinemaDetailsResponseObserver)
            whenever(repository.getMovieDetails(anyInt())).thenAnswer {
                flow
            }
            viewModel.setMovieId(anyInt())
            assertNotNull(viewModel.cinemaDetails.value)
            assertEquals(Resource.error("Check Network Connection!",null),
                viewModel.cinemaDetails.getOrAwaitValueTest())
        }
    }
}



















