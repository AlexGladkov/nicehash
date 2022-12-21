package com.dev.nicehash.data.implementations

import android.util.Log
import com.dev.nicehash.data.converters.ConfigurationConverter
import com.dev.nicehash.data.room.RoomAppDataSource
import com.dev.nicehash.domain.models.Configuration
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import io.reactivex.Single

/**
 * Created by Alex Gladkov on 16.07.18.
 * Implementation for ConfigurationRepository
 */
class ConfigurationRepositoryImpl(val appDataSource: RoomAppDataSource,
                                  val configurationConverter: ConfigurationConverter) : ConfigurationRepository {
    private val TAG = ConfigurationRepositoryImpl::class.java.simpleName

    override fun fetchConfiguration(): Single<Configuration> {
        return Single.create { subscriber ->
            try {
                val configs = appDataSource.configurationDao.getConfigurations()
                if (configs.isEmpty()) {
                    val newConfig = Configuration.defaultInstance()
                    appDataSource.configurationDao.addConfiguration(minerEntity = configurationConverter
                            .modelToDB(configuration = newConfig))
                    subscriber.onSuccess(newConfig)
                } else {
                    subscriber.onSuccess(configurationConverter.dbToModel(configurationEntity = configs.first()))
                }
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }
    }

    override fun updateConfiguration(configuration: Configuration): Single<Boolean> {
        return Single.create { subscriber ->
            try {
//                Log.e(TAG, "configuration $configuration")
                appDataSource.configurationDao.addConfiguration(minerEntity = configurationConverter
                        .modelToDB(configuration = configuration))
                subscriber.onSuccess(true)
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }
    }
}