package com.dev.nicehash.base

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by agladkov on 25.12.17.
 * Base view holder
 */
abstract class BaseViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(model: T)
}