package com.dev.nicehash.data.converters

import com.dev.nicehash.data.providers.models.ApiWorker
import com.dev.nicehash.domain.models.WorkerHub

/**
 * Created by Alex Gladkov on 06.08.18.
 * Converter between api, app and db layer for workers
 */
interface WorkerConverter {
    fun apiToModel(apiWorker: ApiWorker): WorkerHub.Worker
    fun workerToHub(workers: List<WorkerHub.Worker>): List<WorkerHub>
}