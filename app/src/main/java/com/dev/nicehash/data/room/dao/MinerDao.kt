package com.dev.nicehash.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dev.nicehash.contracts.RoomContract
import com.dev.nicehash.data.room.models.MinerEntity

/**
 * Created by Alex Gladkov on 23.06.18.
 * Dao interface for Room Miner Entity
 */
@Dao
interface MinerDao {

    @Query("SELECT * FROM ${RoomContract.TABLE_MINERS}")
    fun getMiners(): List<MinerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMiner(minerEntity: MinerEntity): Long

    @Query("DELETE FROM ${RoomContract.TABLE_MINERS}")
    fun clearMiners()
}