package com.dev.nicehash.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.nicehash.R
import com.dev.nicehash.base.BaseAdapter
import com.dev.nicehash.base.BaseViewHolder
import com.dev.nicehash.domain.models.WorkerHub
import com.dev.nicehash.helpers.ListConfig

/**
 * Created by Alex Gladkov on 15.07.18.
 * Adapter for worker tab
 */
class WorkerHubAdapter : BaseAdapter<WorkerHub>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<WorkerHub> {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_worker_hub, parent, false))
    }

    class ViewHolder(itemView: View): BaseViewHolder<WorkerHub>(itemView = itemView) {
        private val txtTitle = itemView.findViewById<TextView>(R.id.txtWorkerHubTitle)
        private val recyclerWorkerHub = itemView.findViewById<RecyclerView>(R.id.recyclerWorkerHub)
        private val mAdapter = WorkerAdapter()

        override fun bind(model: WorkerHub) {
            txtTitle.text = model.title

            itemView.context?.let {
                val listConfig = ListConfig.Builder(mAdapter)
                        .setHasFixedSize(true)
                        .setHasNestedScroll(false)
                        .build(it)
                listConfig.applyConfig(it, recyclerWorkerHub)
            }

            mAdapter.setList(dataList = model.workerTitles)
        }
    }
}