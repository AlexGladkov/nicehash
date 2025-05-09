package com.dev.nicehash.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dev.nicehash.contracts.RoomContract

/**
 * Created by Alex Gladkov on 11.08.18.
 * Data model for db currency
 */
@Entity(tableName = RoomContract.TABLE_CURRENCY)
data class ExchangeRateEntity(@PrimaryKey(autoGenerate = true) val id: Long?, val value: Double, val code: String)