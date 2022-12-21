package com.dev.nicehash.helpers

/**
 * Created by neura on 11/10/2018.
 */
class MathUtils {

    companion object {
        fun getAverage(collection: Collection<Double>): Double {
            return collection.sum() / collection.size
        }
    }
}