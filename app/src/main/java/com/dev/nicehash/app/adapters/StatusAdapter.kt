package com.dev.nicehash.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.dev.nicehash.R
import com.dev.nicehash.base.BaseAdapter
import com.dev.nicehash.base.BaseViewHolder
import com.dev.nicehash.domain.models.Balance
import com.dev.nicehash.domain.models.Status

/**
 * Created by Alex Gladkov on 15.07.18.
 * Adapter for status activity
 */
class StatusAdapter : BaseAdapter<Status>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Status> {
        return ViewHolder(itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_status, parent, false))
    }

    class ViewHolder(itemView: View): BaseViewHolder<Status>(itemView = itemView) {
        private val txtTitle = itemView.findViewById<TextView>(R.id.txtStatusTitle)
        private val txtHeaderTitle = itemView.findViewById<TextView>(R.id.txtStatusHeaderTitle)
        private val flHeader = itemView.findViewById<FrameLayout>(R.id.flStatusHeader)

        override fun bind(model: Status) {
            txtTitle.text = model.title

            if (model.header.isEmpty()) {
                flHeader.visibility = View.GONE
            } else {
                flHeader.visibility = View.VISIBLE
                txtHeaderTitle.text = model.header
            }
        }
    }
}