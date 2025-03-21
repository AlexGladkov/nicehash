package com.dev.nicehash.base

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Alex Gladkov on 23.06.18.
 * Base adapter for RecyclerView
 */
abstract class BaseAdapter<P>: RecyclerView.Adapter<BaseViewHolder<P>>() {
    private var mDataList: List<P> = ArrayList<P>()
    private var mCallback: BaseAdapterCallback<P>? = null
    var hasItems = false

    fun attachCallback(callback: BaseAdapterCallback<P>) {
        this.mCallback = callback
    }

    fun detachCallback() {
        this.mCallback = null
    }

    fun setList(dataList: List<P>) {
        (mDataList as ArrayList).addAll(dataList)
        hasItems = true
        notifyDataSetChanged()
    }

    fun deleteItem(item: P) {
        val position = mDataList.indexOf(item)
        (mDataList as ArrayList).remove(item)
        notifyItemRemoved(position)
    }

    fun addItems(newItems: List<P>) {
        newItems.forEach {
            addItem(it)
        }
    }

    fun addItem(newItem: P) {
        (mDataList as ArrayList).add(newItem)
        notifyItemInserted(mDataList.size - 1)
    }

    fun addItemToTop(newItem: P) {
        (mDataList as ArrayList).add(0, newItem)
        notifyItemInserted(0)
    }

    fun addItemsToTop(newItems: List<P>) {
        newItems.reversed().forEach {
            addItemToTop(it)
        }
    }

    fun updateItem(position: Int, newItem: P) {
        if (position < mDataList.size) {
            (mDataList as ArrayList)[position] = newItem
            notifyItemChanged(position)
        }
    }

    fun updateItems(itemsList: List<P>) {
        (mDataList as ArrayList).clear()
        setList(itemsList)
    }

    fun removeItem(item: P) {
        val position = mDataList.indexOf(item)
        (mDataList as MutableList).remove(item)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mDataList.size)
    }

    fun removeItemAt(position: Int) {
        (mDataList as MutableList).removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mDataList.size)
    }

    fun insertItemAt(position: Int, newItem: P) {
        (mDataList as MutableList).add(position, newItem)
        notifyItemInserted(position)
    }

    fun getItems(): List<P> = mDataList

    override fun onBindViewHolder(holder: BaseViewHolder<P>, position: Int) {
        holder.bind(mDataList[position])

        holder.itemView.setOnClickListener {
            mCallback?.onItemClick(mDataList[position], holder.itemView)
        }
        holder.itemView.setOnLongClickListener {
            if (mCallback == null) {
                false
            } else {
                mCallback!!.onLongClick(mDataList[position], holder.itemView)
            }

        }
    }

    override fun getItemCount(): Int {
        return mDataList.count()
    }
}