package com.dev.nicehash.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.dev.nicehash.contracts.RoomContract
import com.dev.nicehash.data.room.dao.ConfigurationDao
import com.dev.nicehash.data.room.dao.CurrencyDao
import com.dev.nicehash.data.room.dao.MinerDao
import com.dev.nicehash.data.room.models.ConfigurationEntity
import com.dev.nicehash.data.room.models.ExchangeRateEntity
import com.dev.nicehash.data.room.models.MinerEntity

/**
 * Created by Alex Gladkov on 21.06.18.
 * Class for room implementation
 */
@Database(entities = arrayOf(MinerEntity::class, ConfigurationEntity::class, ExchangeRateEntity::class), version = 10)
abstract class RoomAppDataSource: RoomDatabase() {
    abstract val minerDao: MinerDao
    abstract val configurationDao: ConfigurationDao
    abstract val currencyDao: CurrencyDao

    companion object {
        fun buildDataSource(context: Context): RoomAppDataSource = Room.databaseBuilder(
                context.applicationContext, RoomAppDataSource::class.java, RoomContract.DATABASE_APP)
                .fallbackToDestructiveMigration()
                .build()
    }
}