package com.dev.nicehash.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dev.nicehash.contracts.RoomContract
import com.dev.nicehash.data.room.models.ExchangeRateEntity

/**
 * Created by Alex Gladkov on 11.08.18.
 * Dao interface for Room Currency Entity
 */
@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateCurrency(currencyEntity: ExchangeRateEntity): Long

    @Query("SELECT * FROM ${RoomContract.TABLE_CURRENCY}")
    fun fetchCurrencies(): List<ExchangeRateEntity>
}