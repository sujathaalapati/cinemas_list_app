package com.assesment.cinemaslist.ui.cinema.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.assesment.cinemaslist.MainCoroutinesRule
import com.assesment.cinemaslist.getOrAwaitValueTest
import com.assesment.cinemaslist.model.Cinema
import com.assesment.cinemaslist.repository.CinemaRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CinemaListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutinesRule()




    private lateinit var viewModel: CinemaListViewModel

    private val _res : MutableLiveData<PagingData<Cinema>> = MutableLiveData()
    val res : LiveData<PagingData<Cinema>> = _res

    @Mock
    lateinit var repository: CinemaRepository

    @Mock
    lateinit var movieListResponseObserver: Observer<PagingData<Cinema>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = CinemaListViewModel(repository)
    }

    // first remove cachedIn(viewModelScope) in getAllMovies fun in MovieListViewModel then run this test
    @Test
    fun `when return a movies list successfully`() {
        runTest {
            val flow = flow<PagingData<Cinema>> {
                emit(PagingData.empty())
            }
            flow.collect {
                _res.value = it
            }
            viewModel.cinemas.observeForever(movieListResponseObserver)
            given(repository.getAllMovies()).willReturn(flow)
            verify(repository).getAllMovies()
            viewModel.getAllMovies()
            assertNotNull(viewModel.cinemas.value)
            assertEquals(res.value,viewModel.cinemas.getOrAwaitValueTest())
        }
    }

}

















