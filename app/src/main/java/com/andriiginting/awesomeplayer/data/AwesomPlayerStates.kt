package com.andriiginting.awesomeplayer.data

import com.google.android.exoplayer2.Player

interface AwesomePlayerStates {
    fun onAwesomePlayerVideoDurationRetrieved(duration: Long, player: Player)
    fun onAwesomePlayerVideoBuffering(player: Player)
    fun onAwesomePlayerStarted(player: Player)
    fun onAwesomePlayerFinished(player: Player)
}