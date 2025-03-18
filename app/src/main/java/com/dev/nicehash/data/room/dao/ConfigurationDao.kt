package com.dev.nicehash.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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