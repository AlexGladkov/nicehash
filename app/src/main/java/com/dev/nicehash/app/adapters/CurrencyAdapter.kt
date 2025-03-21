package com.dev.nicehash.app.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dev.nicehash.R
import com.dev.nicehash.base.BaseAdapter
import com.dev.nicehash.base.BaseViewHolder
import com.dev.nicehash.domain.models.Currency
import com.dev.nicehash.domain.models.Status
import com.dev.nicehash.helpers.EnumCollections

/**
 * Created by Alex Gladkov on 15.07.18.
 * Adapter for currency activity
 */
class CurrencyAdapter : BaseAdapter<Currency>() {
    private val TAG = CurrencyAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Currency> {
        return ViewHolder(itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_currency, parent, false))
    }

    fun checkSameFirstPosition(model: Currency): Boolean {
        return getItems()[0].id == model.id
    }

    fun checkIsPopular(model: Currency): Boolean {
        return model.id == EnumCollections.Currency.Euro.id || model.id == EnumCollections.Currency.USD.id
                || model.id == EnumCollections.Currency.BritainPound.id
    }

    fun performPopularClicked(model: Currency) {
        if (getItems()[4].id == EnumCollections.Currency.Australian.id) {
//            getItems().firstOrNull { currency -> model.id == currency.id }?.let {
//                removeItemAt(getItems().indexOf(it))
//            }
//            removeItemAt(position = 1)
//            removeItemAt(position = 1)
//            removeItemAt(position = 1)
//
//            when (model.id) {
//                EnumCollections.Currency.Euro.id -> {
//
//                }
//
//                EnumCollections.Currency.USD.id -> {
//
//                }
//
//                EnumCollections.Currency.BritainPound.id -> {
//
//                }
//            }
        } else {
            removeItemAt(position = 1)
            removeItemAt(position = 1)
        }
    }

    class ViewHolder(itemView: View) : BaseViewHolder<Currency>(itemView = itemView) {
        private val txtTitle = itemView.findViewById<TextView>(R.id.txtCurrencyTitle)
        private val txtHeaderTitle = itemView.findViewById<TextView>(R.id.txtCurrencyHeaderTitle)
        private val txtBtc = itemView.findViewById<TextView>(R.id.txtCurrencyBtc)
        private val flHeader = itemView.findViewById<FrameLayout>(R.id.flCurrencyHeader)
        private val imgFlag = itemView.findViewById<ImageView>(R.id.imgCurrencyFlag)

        override fun bind(model: Currency) {
            txtTitle.text = itemView.context.getString(model.title)
            txtBtc.text = model.btc

            if (model.imgRes > 0) {
                imgFlag.setImageDrawable(ContextCompat.getDrawable(itemView.context, model.imgRes))
            }

            if (model.header == -1) {
                flHeader.visibility = View.GONE
            } else {
                flHeader.visibility = View.VISIBLE
                txtHeaderTitle.text = itemView.context.getString(model.header)
            }
        }
    }
}