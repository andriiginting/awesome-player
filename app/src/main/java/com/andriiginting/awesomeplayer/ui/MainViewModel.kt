package com.andriiginting.awesomeplayer.ui

import androidx.lifecycle.ViewModel
import com.andriiginting.awesomeplayer.data.AwesomePlayerModel
import com.andriiginting.awesomeplayer.data.AwesomePlayerRepository

class MainViewModel(
    private val repository: AwesomePlayerRepository
) : ViewModel() {
    fun getMediaPlayerData(): List<AwesomePlayerModel> = repository.getMockData()
}