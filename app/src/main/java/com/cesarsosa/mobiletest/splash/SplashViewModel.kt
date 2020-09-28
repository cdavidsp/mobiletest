package com.cesarsosa.mobiletest.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesarsosa.mobiletest.characters.data.CharacterRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {

    var liveData: MutableLiveData<String> = MutableLiveData()

    fun initSplashScreen() {
        viewModelScope.launch {
            delay(3500)
            updateLiveData()
        }
    }

    private fun updateLiveData() {
        val splashModel = "End"
        liveData.value = splashModel
    }

}