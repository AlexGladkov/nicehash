package com.dev.nicehash.domain.repositories

import com.dev.nicehash.domain.models.Configuration
import io.reactivex.Single

/**
 * Created by Alex Gladkov on 16.07.18.
 * Repository for Configurations API and DB
 */
interface ConfigurationRepository {
    fun fetchConfiguration(): Single<Configuration>
    fun updateConfiguration(configuration: Configuration): Single<Boolean>
}