package com.dev.nicehash.domain.models

import android.os.Parcelable
import com.dev.nicehash.enums.Theme
import com.dev.nicehash.helpers.EnumCollections
import java.io.Serializable

/**
 * Created by Alex Gladkov on 16.07.18.
 * Model for app configuration (domain level)
 */
data class Configuration(val id: Int, var theme: Theme, var defaultTab: Int, var currency: EnumCollections.Currency,
                         var defaultLanguage: EnumCollections.Language, var isSound: Boolean,
                         var isVibro: Boolean, var isPayments: Boolean, var isMaintenance: Boolean,
                         var isTop: Boolean, var isBottom: Boolean, var isStrix: Boolean, var minerId: Int): Serializable {

    companion object {
        fun defaultInstance(): Configuration {
            return Configuration(id = -1, theme = Theme.LightBlue, defaultTab = 0,
                    defaultLanguage = EnumCollections.Language.English, isSound = true, isMaintenance = true,
                    isPayments = true, isVibro = true, isBottom = true, isStrix = true, isTop = true,
                    minerId = 0, currency = EnumCollections.Currency.USD)
        }
    }
}