package com.dev.nicehash.data.converters

import com.dev.nicehash.data.providers.models.ApiPayment
import com.dev.nicehash.data.room.models.MinerEntity
import com.dev.nicehash.domain.models.Miner
import com.dev.nicehash.domain.models.Payout

/**
 * Created by Alex Gladkov on 23.06.18.
 * Interface for miner converter
 */
interface MinerConverter {
    fun dbToModel(minerEntity: MinerEntity, minerId: Int): Miner
    fun modelToDb(miner: Miner): MinerEntity
}