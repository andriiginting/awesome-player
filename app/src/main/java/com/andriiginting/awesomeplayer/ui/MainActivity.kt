package com.andriiginting.awesomeplayer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.andriiginting.awesomeplayer.R
import com.andriiginting.awesomeplayer.data.AwesomePlayerRepositoryImpl
import com.andriiginting.awesomeplayer.ui.AwesomePlayerViewAdapter.Companion.playIndexThenPausePreviousAndNextPlayers
import com.andriiginting.awesomeplayer.ui.AwesomePlayerViewAdapter.Companion.releaseAllPlayers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var scrollListener: AwesomePlayerScrollListener
    private val awesomeAdapter = AwesomePlayerAdapter()
    private val repository = AwesomePlayerRepositoryImpl()
    private val viewModel by lazy { MainViewModel(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
    }

    override fun onPause() {
        super.onPause()
        releaseAllPlayers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMediaPlayerData()
            .let(awesomeAdapter::setData)
    }

    private fun initRecyclerView() {
        scrollListener = object : AwesomePlayerScrollListener() {
            override fun onItemIsFirstVisibleItem(index: Int) {
                playIndexThenPausePreviousAndNextPlayers(index)
            }
        }

        rvVideoPlayer.apply {
            adapter = awesomeAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            addOnScrollListener(scrollListener)
        }
    }
}
