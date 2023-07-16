/*
 * Copyright 2023 Stanislav Aleshin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * imitations under the License.
 */

package ru.aleshin.mixplayer.presentation.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import kotlinx.coroutines.launch
import ru.aleshin.core.common.navigation.navigator.AppNavigator
import ru.aleshin.core.common.navigation.navigator.NavigatorManager
import ru.aleshin.core.common.platform.activity.BaseActivity
import ru.aleshin.core.common.platform.screen.ScreenContent
import ru.aleshin.core.ui.theme.MixPlayerRes
import ru.aleshin.core.ui.theme.MixPlayerTheme
import ru.aleshin.features.player.api.presentation.player.PlayerService
import ru.aleshin.features.player.api.presentation.player.PlayerServiceBinder
import ru.aleshin.mixplayer.R
import ru.aleshin.mixplayer.application.fetchApp
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainAction
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainEffect
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainEvent
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainViewState
import ru.aleshin.mixplayer.presentation.ui.main.viewmodel.MainViewModel
import ru.aleshin.mixplayer.presentation.ui.mapper.mapToMessage
import ru.aleshin.mixplayer.presentation.ui.splash.SplashScreen
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 12.07.2023.
 */
class MainActivity : BaseActivity<MainViewState, MainEvent, MainAction, MainEffect>() {

    @Inject
    lateinit var navigatorManager: NavigatorManager

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    private var playerService: PlayerService? = null

    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val playerBinder = binder as PlayerServiceBinder
            playerService = playerBinder.fetchService()
            lifecycleScope.launch {
                playerService?.collectInfo { viewModel.dispatchEvent(MainEvent.SendPlayerInfo(it)) }
            }
            lifecycleScope.launch {
                playerService?.collectErrors { viewModel.dispatchEvent(MainEvent.OnPlayerError(it)) }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            playerService = null
        }
    }

    override fun initDI() = fetchApp().appComponent.inject(this)

    @Composable
    override fun Content() = ScreenContent(viewModel, MainViewState()) { state ->
        MixPlayerTheme(
            themeType = state.generalSettings.themeUiType,
            language = state.generalSettings.languageUiType,
        ) {
            val hostState = remember { SnackbarHostState() }
            val coreStrings = MixPlayerRes.strings

            AppNavigator(
                initialScreen = SplashScreen,
                navigatorManager = navigatorManager,
            ) {
                Scaffold(
                    content = { Box(Modifier.padding(it), content = { CurrentScreen() }) },
                    snackbarHost = { SnackbarHost(hostState) }
                )
            }

            handleEffect { effect ->
                when (effect) {
                    is MainEffect.WorkMediaCommand -> {
                        playerService?.workMediaCommand(effect.command)
                    }
                    is MainEffect.ShowPlayerError -> {
                        hostState.showSnackbar(
                            message = effect.error.mapToMessage(coreStrings),
                            withDismissAction = true,
                        )
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onStart() {
        super.onStart()
        setTheme(R.style.Theme_MixPlayer)
        bindService(fetchApp().serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        unbindService(serviceConnection)
        super.onStop()
    }

    override fun onDestroy() {
        if (isFinishing) stopService(fetchApp().serviceIntent)
        super.onDestroy()
    }

    override fun fetchViewModelFactory() = viewModelFactory

    override fun fetchViewModelClass() = MainViewModel::class.java
}
