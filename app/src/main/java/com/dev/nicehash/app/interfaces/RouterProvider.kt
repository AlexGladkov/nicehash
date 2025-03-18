package com.dev.nicehash.app.interfaces

import com.github.terrakok.cicerone.Router


/**
 * Created by Alex Gladkov on 21.06.18.
 * Interface for routing
 */
interface RouterProvider {
    fun getRouter(): Router
}