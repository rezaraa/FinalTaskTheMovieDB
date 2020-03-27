package fariborz.rezara.ViewModelFactory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import fariborz.rezara.repositories.UpcomingRepository
import fariborz.rezara.viewmodels.UpcomingViewModel

/**
Created by Reza*/
class ViewModelUpcomingFactory(private val repository: UpcomingRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpcomingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UpcomingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}