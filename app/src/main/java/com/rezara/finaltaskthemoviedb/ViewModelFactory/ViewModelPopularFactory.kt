package fariborz.rezara.ViewModelFactory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import fariborz.rezara.repositories.PopularRepository
import fariborz.rezara.viewmodels.PopularViewModel

/**
Created by Reza*/
class ViewModelPopularFactory(private val repository: PopularRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PopularViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}