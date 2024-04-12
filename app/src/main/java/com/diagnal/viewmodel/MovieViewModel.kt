package com.diagnal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diagnal.pojo.MovieResponse
import com.diagnal.repository.MovieRepository
import com.diagnal.utils.AssetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private var _movieMutableList = MutableLiveData<AssetResult<MovieResponse>>()
    public val movieMutableList: LiveData<AssetResult<MovieResponse>>
        get() = _movieMutableList

    fun getProductList(pageNo: Int) {
        viewModelScope.launch {
            val response = movieRepository.getMovieList("CONTENTLISTINGPAGE_PAGE$pageNo.json")
            if (response != null && response.page?.content_items?.content?.isNotEmpty() == true && response.page?.content_items?.content?.size!! > 0) {
                _movieMutableList.postValue(AssetResult.Success(response))
            } else {
                _movieMutableList.postValue(AssetResult.Error("Something went wrong"))
            }
        }
    }

}