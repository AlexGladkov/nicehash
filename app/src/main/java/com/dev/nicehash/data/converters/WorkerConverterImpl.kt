package com.dev.nicehash.data.converters

import com.dev.nicehash.data.providers.models.ApiWorker
import com.dev.nicehash.domain.models.WorkerHub
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Alex Gladkov on 06.08.18.
 * Implementation for worker converter
 */
class WorkerConverterImpl: WorkerConverter {

    override fun apiToModel(apiWorker: ApiWorker): WorkerHub.Worker {
        return WorkerHub.Worker(title = apiWorker.title, upDynamic = 0.0f, downDynamic = 0.0f,
                difference = 0.0f, min = apiWorker.time)
    }

    override fun workerToHub(workers: List<WorkerHub.Worker>): List<WorkerHub> {
        val workerHubs = LinkedList<WorkerHub>()
        workers.forEach { worker ->
            if (workerHubs.firstOrNull { hub -> hub.title == worker.title } == null) {
                val titles = ArrayList<WorkerHub.Worker>()
                titles.add(worker)
                workerHubs.add(WorkerHub(title = worker.title, countryCode = "", workerTitles = titles))
            } else {
                val hub = workerHubs.first { hub -> hub.title == worker.title }
                hub.workerTitles.toMutableList().add(worker)
            }
        }

        return workerHubs
    }
}