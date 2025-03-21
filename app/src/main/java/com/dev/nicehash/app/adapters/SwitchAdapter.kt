package com.dev.nicehash.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.dev.nicehash.R
import com.dev.nicehash.app.models.Switch
import com.dev.nicehash.base.BaseAdapter
import com.dev.nicehash.base.BaseViewHolder

/**
 * Created by Alex Gladkov on 25.06.18.
 * Adapter for switchable cells
 */
class SwitchAdapter: BaseAdapter<Switch>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Switch> {
        return ViewHolder(itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_switch, parent, false))
    }

    class ViewHolder(itemView: View): BaseViewHolder<Switch>(itemView = itemView) {
        private val txtTitle = itemView.findViewById<View>(R.id.txtSwitchTitle) as TextView
        private val switcher = itemView.findViewById<View>(R.id.swSwitch) as SwitchCompat

        override fun bind(model: Switch) {
            txtTitle.text = model.title
            switcher.isChecked = model.isChecked
        }
    }
}