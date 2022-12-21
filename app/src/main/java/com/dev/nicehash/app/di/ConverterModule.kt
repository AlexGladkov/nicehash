package com.dev.nicehash.app.di

import com.dev.nicehash.data.converters.*
import dagger.Module
import dagger.Provides

/**
 * Created by Alex Gladkov on 23.06.18.
 * DI module for converters
 */
@Module
class ConverterModule {

    @Provides
    fun provideMinerConverter(): MinerConverter {
        return MinerConverterImpl()
    }

    @Provides
    fun provideConfigurationConverter(): ConfigurationConverter {
        return ConfigurationConverterImpl()
    }

    @Provides
    fun providePaymentsConverter(): PaymentsConverter {
        return PaymentsConverterImpl()
    }

    @Provides
    fun provideWorkerConverter(): WorkerConverter {
        return WorkerConverterImpl()
    }

    @Provides
    fun provideBalanceConverter(): BalanceConverter {
        return BalanceConverterImpl()
    }

    @Provides
    fun provideExchangeRateConverter(): ExchangeRateConverter {
        return ExchangeRateConverterImpl()
    }
}