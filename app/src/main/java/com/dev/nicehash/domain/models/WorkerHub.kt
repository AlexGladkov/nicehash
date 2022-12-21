package com.dev.nicehash.domain.models

/**
 * Created by Alex Gladkov on 15.07.18.
 * Model containing workers for worker tab
 */
data class WorkerHub(val title: String, val countryCode: String, val workerTitles: List<Worker>) {

    /**
     * Created by Alex Gladkov on 15.07.18.
     * Model for Worker Hub
     */
    data class Worker(val title: String, val difference: Float, val min: Int, val upDynamic: Float, val downDynamic: Float)
}