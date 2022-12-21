package com.dev.nicehash.data.room.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.dev.nicehash.contracts.RoomContract

/**
 * Created by Alex Gladkov on 21.06.18.
 * Entity describing CryptoMiner
 */
@Entity(tableName = RoomContract.TABLE_MINERS)
data class MinerEntity(@PrimaryKey(autoGenerate = true) val id: Int?, var name: String, var hash: String,
                       val payouts: String)