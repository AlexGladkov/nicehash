package com.dev.nicehash.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dev.nicehash.R
import com.dev.nicehash.base.BaseAdapter
import com.dev.nicehash.base.BaseViewHolder
import com.dev.nicehash.domain.models.Payout
import java.text.DecimalFormat

/**
 * Created by Alex Gladkov on 15.07.18.
 * Adapter for payout tab
 */
class PayoutAdapter: BaseAdapter<Payout>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Payout> {
        return ViewHolder(itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_payout, parent, false))
    }

    class ViewHolder(itemView: View): BaseViewHolder<Payout>(itemView = itemView) {
        private val txtDate = itemView.findViewById<TextView>(R.id.txtPayoutDate)
        private val txtValue = itemView.findViewById<TextView>(R.id.txtPayoutValue)
        private val txtFeeTitle = itemView.findViewById<TextView>(R.id.txtPayoutFeeTitle)
        private val txtFeeValue = itemView.findViewById<TextView>(R.id.txtPayoutFeeValue)

        @SuppressLint("SetTextI18n")
        override fun bind(model: Payout) {
            txtDate.text = model.dateTime
            txtValue.text = DecimalFormat("##.########").format(model.payBtc)
            txtFeeTitle.text = "${itemView.context.getString(R.string.payout_fee_title)} ${DecimalFormat("##.##").format(model.fee)}%"
            txtFeeValue.text = DecimalFormat("##.########").format(model.feeBtc)
        }
    }
}