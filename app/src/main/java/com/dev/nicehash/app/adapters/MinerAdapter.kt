package com.dev.nicehash.app.adapters

import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.base.BaseAdapter
import com.dev.nicehash.base.BaseViewHolder
import com.dev.nicehash.domain.models.Miner
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * Created by Alex Gladkov on 23.06.18.
 * Adapter for miners list
 * @see ChooseActivity
 */
class MinerAdapter(val minerClick: MinerAdapter.MinerClick): BaseAdapter<Miner>() {

    interface MinerClick {
        fun onEditClick(miner: Miner)
        fun onDeleteClick(miner: Miner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Miner> {
        return ViewHolder(itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_miner, parent, false), minerClick = minerClick)
    }

    class ViewHolder(itemView: View, val minerClick: MinerClick) : BaseViewHolder<Miner>(itemView = itemView) {
        private val TAG = ViewHolder::class.java.simpleName
        private val txtTitle = itemView.findViewById<TextView>(R.id.txtMinerTitle)
        private val txtHash = itemView.findViewById<TextView>(R.id.txtMinerHash)
        private val btnMore = itemView.findViewById<ImageView>(R.id.btnMinerMore)
        private val flMiner = itemView.findViewById<FrameLayout>(R.id.flMiner)

        override fun bind(model: Miner) {
            txtTitle.text = model.name
            txtHash.text = model.hash

            if (App.isDark) {
                if (model.isSelected) {
                    flMiner.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.dark_background_secondary))
                } else {
                    flMiner.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.dark_background_primary))
                }
            } else {
                if (model.isSelected) {
                    flMiner.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.light_background_secondary))
                } else {
                    flMiner.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.light_background_primary))
                }
            }

            btnMore.setOnClickListener {
                val popupMenu = PopupMenu(itemView.context, btnMore)
                val menuInflater = popupMenu.menuInflater

                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.more_edit -> minerClick.onEditClick(miner = model)
                        R.id.more_delete -> minerClick.onDeleteClick(miner = model)
                    }

                    false
                }

                menuInflater.inflate(R.menu.menu_miner_more, popupMenu.menu)
                popupMenu.show()
            }
        }
    }
}