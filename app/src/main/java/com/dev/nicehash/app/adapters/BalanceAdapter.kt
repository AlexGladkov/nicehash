package com.dev.nicehash.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dev.nicehash.R
import com.dev.nicehash.app.servers.ExchangeServer
import com.dev.nicehash.base.BaseAdapter
import com.dev.nicehash.base.BaseViewHolder
import com.dev.nicehash.domain.models.Balance
import java.text.DecimalFormat

/**
 * Created by Alex Gladkov on 15.07.18.
 * Adapter for balance tab
 */
class BalanceAdapter: BaseAdapter<Balance>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Balance> {
        return ViewHolder(itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_balance, parent, false))
    }

    class ViewHolder(itemView: View): BaseViewHolder<Balance>(itemView = itemView) {
        private val txtTitle = itemView.findViewById<TextView>(R.id.txtBalanceTitle)
        private val txtValue = itemView.findViewById<TextView>(R.id.txtBalanceValue)
        private val txtBtcValue = itemView.findViewById<TextView>(R.id.txtBalanceBTCValue)
        private val txtIncomeValue = itemView.findViewById<TextView>(R.id.txtBalanceIncomeValue)
        private val txtIncomeBtcValue = itemView.findViewById<TextView>(R.id.txtBalanceIncomeBTCValue)
        private val txtAlgoSpeed = itemView.findViewById<TextView>(R.id.txtBalanceAlgoSpeed)

        @SuppressLint("SetTextI18n")
        override fun bind(model: Balance) {
            txtTitle.text = model.title
            txtValue.text = DecimalFormat("##.##").format(model.value)
            txtBtcValue.text = DecimalFormat("##.########").format(model.btc)
            txtIncomeValue.text = DecimalFormat("##.##").format(model.incomeValue)
            txtIncomeBtcValue.text = DecimalFormat("##.########").format(model.incomeBtc)
            txtAlgoSpeed.text = "${model.suffix}${itemView.context.getString(R.string.balance_algo_speed)}"
        }
    }
}