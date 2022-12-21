package com.dev.nicehash.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dev.nicehash.R
import com.dev.nicehash.base.BaseAdapter
import com.dev.nicehash.base.BaseViewHolder
import com.dev.nicehash.domain.models.WorkerHub

/**
 * Created by Alex Gladkov on 15.07.18.
 * Subclass for worker
 */
class WorkerAdapter: BaseAdapter<WorkerHub.Worker>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<WorkerHub.Worker> {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_worker, parent, false))
    }

    class ViewHolder(itemView: View): BaseViewHolder<WorkerHub.Worker>(itemView = itemView) {
        private val txtTitle = itemView.findViewById<TextView>(R.id.txtWorkerTitle)
        private val txtSpeed = itemView.findViewById<TextView>(R.id.txtWorkerSpeed)
        private val txtSpeedValue = itemView.findViewById<TextView>(R.id.txtWorkerSpeedValue)
        private val txtDiff = itemView.findViewById<TextView>(R.id.txtWorkerDiff)
        private val txtDiffValue = itemView.findViewById<TextView>(R.id.txtWorkerDiffValue)
        private val txtDiffTitle = itemView.findViewById<TextView>(R.id.txtWorkerDiffTitle)
        private val txtTime = itemView.findViewById<TextView>(R.id.txtWorkerTime)

        @SuppressLint("SetTextI18n")
        override fun bind(model: WorkerHub.Worker) {
            txtTitle.text = model.title
            txtDiff.text = " ${model.difference}"
            txtSpeedValue.text = "+ ${model.upDynamic}"
            txtSpeed.text = "MH/s"
            txtDiffValue.text = "– ${model.downDynamic}"
            txtDiffTitle.text = "MH/s"
            txtTime.text = "${model.min}"
        }
    }
}