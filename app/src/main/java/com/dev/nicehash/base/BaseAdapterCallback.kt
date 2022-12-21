package com.dev.nicehash.base

import android.view.View

/**
 * Created by Alex Gladkov on 23.06.18.
 * Default operation for recycler view adapters
 */
interface BaseAdapterCallback<T> {
    fun onItemClick(model: T, view: View)
    fun onLongClick(model: T, view: View): Boolean
}