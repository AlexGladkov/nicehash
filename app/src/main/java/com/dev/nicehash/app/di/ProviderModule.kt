package com.dev.nicehash.app.di

import com.dev.nicehash.data.providers.CurrencyProvider
import com.dev.nicehash.data.providers.GeneralProvider
import com.dev.nicehash.data.providers.MinerProvider
import com.dev.nicehash.data.providers.NicehashProvider
import com.dev.nicehash.data.providers.implementations.CurrencyProviderImpl
import com.dev.nicehash.data.providers.implementations.GeneralProviderImpl
import com.dev.nicehash.data.providers.implementations.MinerProviderImpl
import com.dev.nicehash.data.providers.implementations.NicehashProviderImpl
import com.dev.nicehash.data.providers.services.RemoteCurrencyService
import com.dev.nicehash.data.providers.services.RemoteGeneralService
import com.dev.nicehash.data.providers.services.RemoteMinerService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Alex Gladkov on 05.08.18.
 * DI class for providers
 */
@Module
class ProviderModule {

    @Provides
    @Singleton
    fun createNicehashProvider(remoteMinerService: RemoteMinerService): NicehashProvider {
        return NicehashProviderImpl(remoteMinerService = remoteMinerService)
    }

    @Provides
    fun createGeneralProvider(remoteGeneralService: RemoteGeneralService): GeneralProvider {
        return GeneralProviderImpl(remoteGeneralService = remoteGeneralService)
    }

    @Provides
    fun createMinerProvider(remoteMinerService: RemoteMinerService, gson: Gson): MinerProvider {
        return MinerProviderImpl(remoteMinerService = remoteMinerService, gson = gson)
    }

    @Provides
    fun createCurrencyProvider(remoteCurrencyService: RemoteCurrencyService): CurrencyProvider {
        return CurrencyProviderImpl(remoteCurrencyService = remoteCurrencyService)
    }
}