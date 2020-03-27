package fariborz.rezara.ViewModelFactory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import fariborz.rezara.repositories.MovieDetailsRepository
import fariborz.rezara.viewmodels.MovieDetailsViewModel

/**
Created by Reza*/
class ViewModelDetailFactory(private val repository: MovieDetailsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}