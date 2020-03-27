package fariborz.rezara.ViewModelFactory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import fariborz.rezara.repositories.NowShowingRepository
import fariborz.rezara.viewmodels.NowShowingViewModel

/**
Created by Reza*/
class ViewModelNowShowingFactory(private val repository: NowShowingRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NowShowingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NowShowingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}