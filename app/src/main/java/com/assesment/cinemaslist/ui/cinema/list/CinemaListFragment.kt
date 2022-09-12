package com.assesment.cinemaslist.ui.cinema.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.assesment.cinemaslist.databinding.FragmentCinemaListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class CinemaListFragment : Fragment() {

    val viewModel: CinemaListViewModel by viewModels()
    private lateinit var binding: FragmentCinemaListBinding
    lateinit var recyclerAdapter : CinemaRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCinemaListBinding.inflate(layoutInflater)
        Log.i("baso", "onCreateView: ")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        subscribeObservers()
        retry()

        Log.i("baso", "onViewCreated: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i("baso", "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i("baso", "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i("baso", "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("baso", "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("baso", "onDestroy: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("baso", "onDetach: ")
    }

    private fun snackBarClickedMovie(title: String) {
        val parentLayout = requireActivity().findViewById<View>(android.R.id.content)
        Snackbar.make(parentLayout, title, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun initRecyclerView() {

        binding.movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            recyclerAdapter =
                CinemaRecyclerAdapter(requireContext()) { movieId: Int ->
                    findNavController().navigate(
                        CinemaListFragmentDirections
                            .actionMovieListFragmentToMovieDetailsFragment(movieId = movieId )
                    )
                        //title: String -> snackBarClickedMovie(title)
                }

            /*addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    viewModel.onChangeMovieScrollPosition(lastPosition)
                    val page = lastPosition / 10
                    viewModel.setPage(page)
                }
            })*/

            adapter = recyclerAdapter

        }

        binding.movieRecyclerView.adapter = recyclerAdapter.withLoadStateFooter(
            footer = CineLoadStateAdapter(recyclerAdapter::retry)
        )

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            recyclerAdapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.Loading) {

                    if (recyclerAdapter.snapshot().isEmpty()) {
                        binding.progressBar.isVisible = true
                    }
                    binding.txtError.isVisible = false
                    binding.btnRetry.isVisible = false
                } else {
                    binding.progressBar.isVisible = false

                    val error = when {
                        loadState.source.prepend is LoadState.Error -> loadState.source.prepend as LoadState.Error
                        loadState.source.append is LoadState.Error -> loadState.source.append as LoadState.Error
                        loadState.source.refresh is LoadState.Error -> loadState.source.refresh as LoadState.Error
                        else -> null
                    }
                    error?.let {
                        if (recyclerAdapter.snapshot().isEmpty()) {
                            binding.txtError.isVisible = true
                            binding.btnRetry.isVisible = true
                            binding.txtError.text = "Check Network Connection!"
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            recyclerAdapter.loadStateFlow
            .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                //.collect { binding.movieRecyclerView.scrollToPosition(0) }
        }
    }

    private fun subscribeObservers() {
        viewModel.cinemas.observe(viewLifecycleOwner){
            recyclerAdapter.submitData(lifecycle,it)
        }
        /*viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.movies.collectLatest {
                recyclerAdapter.submitData(it)
            }
        }*/
    }

    private fun retry() {
        binding.btnRetry.setOnClickListener { recyclerAdapter.retry() }
    }

}