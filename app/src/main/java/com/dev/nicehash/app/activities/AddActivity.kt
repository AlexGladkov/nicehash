package com.dev.nicehash.app.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.dev.nicehash.R
import com.dev.nicehash.app.App
import com.dev.nicehash.base.BaseActivity
import com.dev.nicehash.domain.models.Miner
import com.dev.nicehash.domain.repositories.ConfigurationRepository
import com.dev.nicehash.domain.repositories.MinerRepository
import com.dev.nicehash.enums.Keys
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add.*
import javax.inject.Inject

/**
 * Created by Alex Gladkov on 15.07.18.
 * Activity for adding miners
 */
class AddActivity : BaseActivity() {
    private val TAG = AddActivity::class.java.simpleName
    private var isEditing = false

    // MARK: - Repositories
    @Inject lateinit var minerRepository: MinerRepository
    @Inject lateinit var configurationRepository: ConfigurationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(activity = this@AddActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        if ((intent?.extras?.get(Keys.Miner.value) as? Bundle) != null) {
            val bundle = intent?.extras?.get(Keys.Miner.value) as? Bundle
            val miner = bundle?.get(Keys.Miner.value) as? Miner
            txtAddName.setText(miner?.name.orEmpty())
            txtAddHash.setText(miner?.hash.orEmpty())
            txtAddTitle.text = getString(R.string.edit_miner_title)
            isEditing = true
        }

        btnAddApply.setOnClickListener {
            if (isEditing) {
                minerRepository.updateMiner(miner = (intent?.extras?.get(Keys.Miner.value) as? Bundle)
                        ?.get(Keys.Miner.value) as? Miner, newName = txtAddName.text.toString(), newHash = txtAddHash.text.toString())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Toast.makeText(applicationContext, getString(R.string.toast_saved), Toast.LENGTH_LONG).show()
                            onBackPressed()
                        }, { error ->
                            error.printStackTrace()
                            Toast.makeText(applicationContext, "error " + error.localizedMessage, Toast.LENGTH_LONG).show()
                        })
            } else {
                configurationRepository.fetchConfiguration()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ configuration ->
                            minerRepository.addMiner(Miner(id = -1, name = txtAddName.text.toString(),
                                    hash = txtAddHash.text.toString(), isSelected = false))
                                    .subscribeOn(Schedulers.computation())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({ minerId ->
                                        configuration.minerId = minerId.toInt()
                                        configurationRepository.updateConfiguration(configuration = configuration)
                                                .subscribeOn(Schedulers.computation())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe({
                                                    val splashIntent = Intent(applicationContext, SplashActivity::class.java)
                                                    splashIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                                            Intent.FLAG_ACTIVITY_NEW_TASK)
                                                    startActivity(splashIntent)
                                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                                                    finishAffinity()
                                                }, {
                                                    Toast.makeText(applicationContext, "$TAG, Error update configuration",
                                                            Toast.LENGTH_LONG).show()
                                                })
                                    }, {
                                        Toast.makeText(applicationContext, "$TAG, Error add miner",
                                                Toast.LENGTH_LONG).show()
                                    })
                        }, {
                            Toast.makeText(applicationContext, "$TAG, Error load configuration",
                                    Toast.LENGTH_LONG).show()
                        })
            }
        }
    }
}