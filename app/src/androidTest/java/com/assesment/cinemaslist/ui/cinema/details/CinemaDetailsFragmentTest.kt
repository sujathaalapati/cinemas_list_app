package com.assesment.cinemaslist.ui.cinema.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.assesment.cinemaslist.R
import com.assesment.cinemaslist.launchFragmentInHiltContainer
import com.assesment.cinemaslist.AndroidMainCoroutinesRule
import com.assesment.cinemaslist.model.CinemaDetails
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class CinemaDetailsFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = AndroidMainCoroutinesRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val movie = CinemaDetails(
        id = 1,
        title = "The Shawshank Redemption",
        poster = "poster1",
        year = "1994",
        country = "USA",
        rating = "9.3",
        rated = "R",
        released = "14 Oct 1994",
        runtime = "142 min",
        director = "Frank Darabont",
        writer = "Stephen King (short story \"Rita Hayworth and Shawshank Redemption\"), Frank Darabont (screenplay)",
        actors = "Tim Robbins, Morgan Freeman, Bob Gunton, William Sadler",
        plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
        awards = "Nominated for 7 Oscars. Another 19 wins & 30 nominations.",
        votes = "1,738,596",
        genres = listOf("Crime","Drama"),
        images = listOf("http://moviesapi.ir/images/tt0111161_screenshot1.jpg"))

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun isMovieDetailsVisible() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<CinemaDetailsFragment>(bundleOf("movie_id" to 1)) {
            Navigation.setViewNavController(requireView(), navController)

            setMovieProperties(
                movie.actors,movie.awards,movie.country,movie.director,
                movie.plot,movie.rating,movie.released,movie.runtime,
                movie.title,movie.writer,movie.year, movie.genres!!)
        }


        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_title)).check(matches(withText(movie.title)))
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<CinemaDetailsFragment>(bundleOf("movie_id" to 1)) {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }


}






























