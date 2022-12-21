package com.dev.nicehash.data.providers.implementations

import android.util.Log
import com.dev.nicehash.data.providers.GeneralProvider
import com.dev.nicehash.data.providers.services.RemoteGeneralService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Alex Gladkov on 05.08.18.
 * Implementation for GeneralProvider interface
 */
class GeneralProviderImpl(private val remoteGeneralService: RemoteGeneralService): GeneralProvider {
    private val TAG = GeneralProviderImpl::class.java.simpleName

    override fun getVersion() {
        remoteGeneralService.getVersion()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    Log.e(TAG, "result $result")
                }, { error ->
                    Log.e(TAG, "error $error")
                })
    }
}