package com.dev.nicehash.app.di

import com.dev.nicehash.app.activities.*
import com.dev.nicehash.app.fragments.BalanceFragment
import com.dev.nicehash.app.fragments.IncomeFragment
import com.dev.nicehash.app.fragments.PayoutsFragment
import com.dev.nicehash.app.fragments.WorkersFragment
import com.dev.nicehash.app.views.PurchaseView
import com.dev.nicehash.base.BaseContainer
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Alex Gladkov on 21.06.18.
 * Dagger app component
 */
@Component(modules = arrayOf(AppModule::class, RoomModule::class, ProviderModule::class,
        NavigationModule::class, FirebaseModule::class, RemoteModule::class,
        LocalNavigationModule::class, RepositoryModule::class, ConverterModule::class))
@Singleton
interface AppComponent {

    // Activities
    fun inject(activity: MainActivity)
    fun inject(activity: ChooseActivity)
    fun inject(activity: SettingsActivity)
    fun inject(activity: AddActivity)
    fun inject(activity: SplashActivity)
    fun inject(activity: NotificationsActivity)
    fun inject(activity: ThemesActivity)
    fun inject(activity: ServiceStatusActivity)
    fun inject(activity: CurrenciesActivity)
    fun inject(activity: PurchasesActivity)

    // Fragments
    fun inject(fragment: BaseContainer)
    fun inject(fragment: PayoutsFragment)
    fun inject(fragment: WorkersFragment)
    fun inject(fragment: BalanceFragment)
    fun inject(fragment: IncomeFragment)
}