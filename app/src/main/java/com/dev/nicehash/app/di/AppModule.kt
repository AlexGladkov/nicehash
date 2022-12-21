package com.dev.nicehash.app.di

import android.content.Context
import com.dev.nicehash.app.App
import com.dev.nicehash.app.servers.ExchangeServer
import com.dev.nicehash.app.servers.ExchangeServerImpl
import com.dev.nicehash.domain.repositories.CurrencyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Alex Gladkov on 21.06.18.
 * App module provides context from App
 */
@Module
class AppModule(private val app: App) {
    @Provides @Singleton fun provideContext(): Context = app
}