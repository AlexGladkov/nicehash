package com.dev.nicehash.data.implementations

import com.dev.nicehash.data.providers.GeneralProvider
import com.dev.nicehash.domain.repositories.GeneralRepository

/**
 * Created by Alex Gladkov on 05.08.18.
 * General repository implementation
 */
class GeneralRepositoryImpl(val generalProvider: GeneralProvider): GeneralRepository {

    override fun getVersion() {
        generalProvider.getVersion()
    }
}