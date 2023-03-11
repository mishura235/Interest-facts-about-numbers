package com.example.numbersfact

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.numbersfact.api.ApiRequests
import com.example.numbersfact.api.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class FactViewModel : ViewModel() {

    private val retrofit = ApiService.retrofitService

    private var _listOfFacts: MutableLiveData<MutableList<String>> =
        MutableLiveData(mutableListOf())
    private var _fact: MutableLiveData<String> = MutableLiveData()

    val listOfFacts: MutableLiveData<MutableList<String>>
        get() = _listOfFacts

    val fact: MutableLiveData<String>
        get() = _fact


    @SuppressLint("SetTextI18n")
    fun updateFact(num: String) {
        CoroutineScope(Dispatchers.IO).launch {
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

    fun updateFactList() {
        CoroutineScope(Dispatchers.IO).launch {
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
}