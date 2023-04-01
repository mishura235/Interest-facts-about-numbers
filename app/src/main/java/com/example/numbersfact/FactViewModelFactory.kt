package com.example.numbersfact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.numbersfact.api.ApiRequests
import retrofit2.Retrofit

class FactViewModelFactory(private val retrofit: ApiRequests) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FactViewModel(retrofit) as T
    }
}