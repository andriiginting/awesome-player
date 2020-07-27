package com.andriiginting.awesomeplayer.ui

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.andriiginting.awesomeplayer.data.AwesomePlayerStates
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class AwesomePlayerViewAdapter {

    companion object {
        private var playersMap: MutableMap<Int, SimpleExoPlayer> = mutableMapOf()
        fun releaseAllPlayers() {
            playersMap.map {
                it.value.release()
            }
        }

        fun playIndexThenPausePreviousAndNextPlayers(index: Int) {
            if (index != 0) {
                if (playersMap[index]?.playWhenReady == false) playersMap[index]?.playWhenReady =
                    true
                if (playersMap[index + 1]?.playWhenReady == true) playersMap[index + 1]?.playWhenReady =
                    false
                if (playersMap[index - 1]?.playWhenReady == true) playersMap[index - 1]?.playWhenReady =
                    false
            } else {
                if (playersMap[index]?.playWhenReady == false) playersMap[index]?.playWhenReady =
                    true
                if (playersMap[index + 1]?.playWhenReady == true) playersMap[index + 1]?.playWhenReady =
                    false
            }
        }

        /*
        *  url is a url of stream video
        *  progressbar for show when start buffering stream
        * thumbnail for show before video start
        * */
        @JvmStatic
        @BindingAdapter(
            value = ["video_url", "on_state_change", "progressbar", "thumbnail", "item_index"],
            requireAll = false
        )
        fun PlayerView.loadVideo(
            url: String,
            callback: AwesomePlayerStates,
            progressbar: ProgressBar,
            thumbnail: ImageView,
            item_index: Int? = null
        ) {
            if (url == null) return
            val player = ExoPlayerFactory.newSimpleInstance(
                context, DefaultRenderersFactory(context), DefaultTrackSelector(),
                DefaultLoadControl()
            )

            player.playWhenReady = true
            player.repeatMode = Player.REPEAT_MODE_ALL
            // When changing track, retain the latest frame instead of showing a black screen
            setKeepContentOnPlayerReset(true)
            // We'll show the controller
            this.useController = false
            // Provide url to load the video from here
            val mediaSource = ExtractorMediaSource.Factory(
                DefaultHttpDataSourceFactory("Demo")
            ).createMediaSource(Uri.parse(url))

            player.prepare(mediaSource)

            this.player = player

            // add player with its index to map
            if (playersMap.containsKey(item_index))
                playersMap.remove(item_index)
            if (item_index != null)
                playersMap[item_index] = player

            this.player!!.addListener(object : Player.EventListener {

                override fun onPlayerError(error: ExoPlaybackException) {
                    super.onPlayerError(error)
                    Log.e("AwesomePlayer", "Oops! Error occurred while playing media.")
                }

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    super.onPlayerStateChanged(playWhenReady, playbackState)

                    if (playbackState == Player.STATE_BUFFERING) {
                        callback.onAwesomePlayerVideoBuffering(player)
                        // Buffering..
                        // set progress bar visible here
                        // set thumbnail visible
                        thumbnail.visibility = View.VISIBLE
                        progressbar.visibility = View.VISIBLE
                    }

                    if (playbackState == Player.STATE_READY) {
                        // [PlayerView] has fetched the video duration so this is the block to hide the buffering progress bar
                        progressbar.visibility = View.GONE
                        // set thumbnail gone
                        thumbnail.visibility = View.GONE
                        callback.onAwesomePlayerVideoDurationRetrieved(
                            this@loadVideo.player!!.duration,
                            player
                        )
                    }

                    if (playbackState == Player.STATE_READY && player.playWhenReady) {
                        // [PlayerView] has started playing/resumed the video
                        callback.onAwesomePlayerStarted(player)
                    }
                }
            })
        }
    }
}