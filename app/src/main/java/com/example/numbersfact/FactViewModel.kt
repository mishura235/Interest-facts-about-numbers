package com.example.numbersfact

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.numbersfact.api.ApiRequests
import com.example.numbersfact.api.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse


class FactViewModel(private val retrofit: ApiRequests) : ViewModel() {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private var _listOfFacts: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

    private var _fact: MutableLiveData<String> = MutableLiveData()

    val listOfFacts: MutableLiveData<MutableList<String>>
        get() = _listOfFacts

    val fact: MutableLiveData<String>
        get() = _fact



    fun updateFactByNumber(num: String) {
        scope.launch {
            try {
                if (num == "random") {
                    val response = retrofit.getRandomFact().awaitResponse()
                    val factAboutNum = response.body()!!
                    _fact.postValue(factAboutNum.text)
                } else {
                    val response = retrofit.getFact(num).awaitResponse()
                    val factAboutNum = response.body()!!
                    _fact.postValue(factAboutNum.text)
                }
            } catch (e: java.lang.Exception) {
                Log.d("ViewModel", "Error in updateFact")
            }
        }
    }
    fun updateFactRandomized() {
        scope.launch {
            try {
                val response = retrofit.getRandomFact().awaitResponse()
                val factAboutNum = response.body()!!
                _fact.postValue(factAboutNum.text)
            } catch (e: java.lang.Exception) {
                Log.d("ViewModel", "Error in updateFact")
            }
        }
    }

    fun updateFactList() {
        scope.launch {
            try {
                val result = mutableListOf<String>()
                repeat(10) {
                    val response = retrofit.getRandomFact().awaitResponse()
                    val factAboutNum = response.body()!!.text
                    result.add(factAboutNum)
                }
                _listOfFacts.postValue(result)
            } catch (e: java.lang.Exception) {
                Log.d("ViewModel", "Error in updateFactList")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}