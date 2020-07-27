package com.andriiginting.awesomeplayer.ui

import androidx.recyclerview.widget.RecyclerView
import com.andriiginting.awesomeplayer.data.AwesomePlayerModel
import com.andriiginting.awesomeplayer.data.AwesomePlayerStates
import com.andriiginting.awesomeplayer.databinding.AwesomePlayerItemsBinding

class AwesomePlayerViewHolder(
    private val view: AwesomePlayerItemsBinding
) : RecyclerView.ViewHolder(view.root) {

    fun setData(data: AwesomePlayerModel, states: AwesomePlayerStates, position: Int) {
        view.apply {
            dataModel = data
            callback = states
            index = position
            executePendingBindings()
        }
    }
}