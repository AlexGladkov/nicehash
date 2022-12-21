package com.dev.nicehash.contracts

/**
 * Created by Alex Gladkov on 21.06.18.
 * Contract for room database (basename, queries, etc)
 */
class RoomContract {

    companion object {
        const val DATABASE_APP = "nicehashmonitor.db"

        const val TABLE_MINERS = "miners"
        const val TABLE_CONFIGURATIONS = "configurations"
        const val TABLE_CURRENCY = "currency"
    }
}