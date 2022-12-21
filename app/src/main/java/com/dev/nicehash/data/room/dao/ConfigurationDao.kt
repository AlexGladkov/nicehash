package com.dev.nicehash.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dev.nicehash.contracts.RoomContract
import com.dev.nicehash.data.room.models.ConfigurationEntity
import com.dev.nicehash.data.room.models.MinerEntity

/**
 * Created by Alex Gladkov on 16.07.18
 * Dao for configuration entity
 */
@Dao
interface ConfigurationDao {

    @Query("SELECT * FROM ${RoomContract.TABLE_CONFIGURATIONS}")
    fun getConfigurations(): List<ConfigurationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addConfiguration(minerEntity: ConfigurationEntity)
}