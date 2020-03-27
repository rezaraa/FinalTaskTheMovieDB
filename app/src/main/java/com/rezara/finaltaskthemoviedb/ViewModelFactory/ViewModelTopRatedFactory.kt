package fariborz.rezara.ViewModelFactory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import fariborz.rezara.repositories.TopRatedRepository
import fariborz.rezara.viewmodels.TopRatedViewModel

/**
Created by Reza*/
class ViewModelTopRatedFactory(private val repository: TopRatedRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopRatedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TopRatedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}