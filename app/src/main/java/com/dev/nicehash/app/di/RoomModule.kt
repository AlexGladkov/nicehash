package com.dev.nicehash.app.di

import android.content.Context
import com.dev.nicehash.data.room.RoomAppDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Alex Gladkov on 21.06.18.
 * Room injections
 */
@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomCurrencyDataSource(context: Context) =
            RoomAppDataSource.buildDataSource(context)
}