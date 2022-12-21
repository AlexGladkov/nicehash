package com.dev.nicehash.domain.models

import com.dev.nicehash.helpers.EnumCollections

/**
 * Created by Alex Gladkov on 25.07.18.
 * Model for currency (domain level)
 */
data class Currency(val id: Int, val title: Int, val btc: String,
                    val header: Int, val imgRes: Int, val prototype: EnumCollections.Currency)