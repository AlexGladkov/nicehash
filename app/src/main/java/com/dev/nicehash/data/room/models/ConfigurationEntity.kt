package com.dev.nicehash.data.room.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.dev.nicehash.contracts.RoomContract
import com.dev.nicehash.helpers.EnumCollections

/**
 * Created by Alex Gladkov on 16.07.18.
 * Class for app configuration
 */
@Entity(tableName = RoomContract.TABLE_CONFIGURATIONS)
data class ConfigurationEntity(@PrimaryKey val id: Int, val theme: Int, val defaultTab: Int,
                               val defaultLanguage: Int, var isSound: Boolean,
                               var isVibro: Boolean, var isPayments: Boolean, var isMaintenance: Boolean,
                               var isTop: Boolean, var isBottom: Boolean, var isStrix: Boolean, var minerId: Int,
                               var currency: Int)
