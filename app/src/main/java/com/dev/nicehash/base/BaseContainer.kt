package com.dev.nicehash.base

import android.os.Bundle
import com.dev.nicehash.app.App
import com.dev.nicehash.app.interfaces.BackButtonListener
import com.dev.nicehash.app.interfaces.RouterProvider
import com.dev.nicehash.app.navigation.LocalCiceroneHolder
import com.dev.nicehash.enums.Keys
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import moxy.MvpAppCompatFragment
import javax.inject.Inject

/**
 * Created by agladkov on 11.01.18.
 * Use this for tabs fragments (ex. ServiceContainer, ProfileContainer, etc)
 */
abstract class BaseContainer: MvpAppCompatFragment(), RouterProvider, BackButtonListener {
    private val TAG: String = BaseContainer::class.java.simpleName
    private val containerName = arguments?.getString(Keys.Name.value)

    @Inject
    lateinit var ciceroneHolder: LocalCiceroneHolder

    abstract fun doubleTap()
    abstract fun updateContainer()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this@BaseContainer)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
//        getCicerone().navigatorHolder.setNavigator(getNavigator())
    }

    override fun onPause() {
//        getCicerone().navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun getCicerone(): Cicerone<Router> {
        return ciceroneHolder.getCicerone(containerName.toString())
    }

    open fun getNavigator(): Navigator? {
        throw NotImplementedError()
    }

    // MARK: - BackButtonListener implementation
    override fun onBackPressed(): Boolean {
        activity?.let { (it as RouterProvider).getRouter().exit() }
        return true
    }

    // MARK: - RouterProvider
    override fun getRouter(): Router {
        return getCicerone().router
    }
}