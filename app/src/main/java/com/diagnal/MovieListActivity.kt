package com.diagnal

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diagnal.adapter.MovieAdapter
import com.diagnal.databinding.ActivityMovieListBinding
import com.diagnal.utils.AssetResult
import com.diagnal.utils.GridSpacingItemDecoration
import com.diagnal.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    lateinit var adapterMovie: MovieAdapter
    private lateinit var binding: ActivityMovieListBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private var pageNo = 1
    private var isLastPage = false
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        handleObserver()
    }


    private fun initViews() {

        // adding movie adapter
        binding.rvMovie.layoutManager = getLayoutManager()
        adapterMovie = MovieAdapter(applicationContext)
        binding.rvMovie.adapter = adapterMovie
        binding.rvMovie.addOnScrollListener(recyclerViewOnScrollListener)

        binding.tvBack.setOnClickListener {
            finish()
        }

        binding.tvSearch.setOnClickListener {
            binding.rltSearch.visibility = View.VISIBLE
            binding.rltTitle.visibility = View.GONE
        }

        binding.tvCancel.setOnClickListener {
            binding.etSearch.setText("")
            adapterMovie.setSearchText("")
            binding.rltSearch.visibility = View.GONE
            binding.rltTitle.visibility = View.VISIBLE
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(srt: Editable?) {
                if (srt.toString().length > 2) {
                    adapterMovie.setSearchText(srt.toString())
                }
            }

        })

        movieViewModel.getProductList(pageNo)

    }

    private fun handleObserver() { // Getting movie list
        movieViewModel.movieMutableList.observe(this) {
            when (it) {
                is AssetResult.Error -> {
                    isLoading = false
                }

                is AssetResult.Loading -> {
                }

                is AssetResult.Success -> {
                    val movieResponse = it.data!!
                    isLoading = false
                    isLastPage =
                        movieResponse.page?.page_size?.toInt()!! < 20
                    val movieList = movieResponse.page?.content_items?.content
                    print(movieList.toString())
                    if (movieList != null) {
                        adapterMovie.submitList(movieList)
                    }
                }
            }
        }
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager {
        val orientation = resources.configuration.orientation
        val spanCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 5 else 3
        binding.rvMovie.addItemDecoration(GridSpacingItemDecoration(spanCount))
        return GridLayoutManager(this, spanCount)
    }

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() { // Pagination
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val visibleItemCount = layoutManager.findLastVisibleItemPosition()
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            if (!isLoading && !isLastPage) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 5 /*&& totalItemCount >= PAGE_SIZE*/) {
                    pageNo += 1
                    isLoading = true
                    movieViewModel.getProductList(pageNo)
                }
            }
        }
    }

}