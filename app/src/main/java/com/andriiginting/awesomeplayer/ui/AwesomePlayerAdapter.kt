package com.andriiginting.awesomeplayer.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.andriiginting.awesomeplayer.R
import com.andriiginting.awesomeplayer.data.AwesomePlayerModel
import com.andriiginting.awesomeplayer.data.AwesomePlayerStates
import com.andriiginting.awesomeplayer.databinding.AwesomePlayerItemsBinding
import com.google.android.exoplayer2.Player

class AwesomePlayerAdapter : AwesomePlayerStates, RecyclerView.Adapter<AwesomePlayerViewHolder>() {

    private var list: List<AwesomePlayerModel> = listOf()

    override fun onAwesomePlayerVideoDurationRetrieved(duration: Long, player: Player) {}
    override fun onAwesomePlayerVideoBuffering(player: Player) {}
    override fun onAwesomePlayerStarted(player: Player) {}
    override fun onAwesomePlayerFinished(player: Player) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AwesomePlayerViewHolder {
        return DataBindingUtil.inflate<AwesomePlayerItemsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.awesome_player_items,
            parent,
            false
        ).let(::AwesomePlayerViewHolder)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AwesomePlayerViewHolder, position: Int) {
        holder.setData(list[position], this, position)
    }

    fun setData(list: List<AwesomePlayerModel>) {
        this.list = list
        notifyDataSetChanged()
    }
}