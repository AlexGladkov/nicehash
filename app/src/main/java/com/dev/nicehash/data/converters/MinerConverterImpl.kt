package com.dev.nicehash.data.converters

import android.util.Log
import com.dev.nicehash.data.room.models.MinerEntity
import com.dev.nicehash.domain.models.Miner

/**
 * Created by Alex Gladkov on 23.06.18.
 * Implementation for converter interface
 * @see MinerConverter
 */
class MinerConverterImpl: MinerConverter {
    private val TAG = MinerConverterImpl::class.java.simpleName

    override fun dbToModel(minerEntity: MinerEntity, minerId: Int): Miner {
        return Miner(id = minerEntity.id ?: -1, name = minerEntity.name, hash = minerEntity.hash,
                isSelected = minerEntity.id == minerId)
    }

    override fun modelToDb(miner: Miner): MinerEntity {
        return MinerEntity(id = miner.id, name = miner.name, hash = miner.hash, payouts = "")
    }
}