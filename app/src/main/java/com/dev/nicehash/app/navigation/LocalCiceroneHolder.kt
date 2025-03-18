package com.dev.nicehash.app.navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

/**
 * Created by agladkov on 11.01.18.
 * Performs subnNvigation for holding fragments state when clicks activity holder tabs
 */
class LocalCiceroneHolder {
    private val containers: HashMap<String, Cicerone<Router>> = HashMap()

    fun getCicerone(containerTag: String): Cicerone<Router> {
        if (!containers.containsKey(containerTag)) {
            containers.put(containerTag, Cicerone.create())
        }

        return containers[containerTag]!!
    }
}