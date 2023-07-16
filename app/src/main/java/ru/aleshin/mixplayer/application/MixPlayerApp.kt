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

package ru.aleshin.mixplayer.application

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.aleshin.core.common.functional.Constants
import ru.aleshin.core.common.notifications.parameters.NotificationPriority
import ru.aleshin.features.player.api.presentation.player.PlayerService
import ru.aleshin.mixplayer.di.component.AppComponent

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
class MixPlayerApp : Application() {

    val appComponent by lazy {
        AppComponent.create(applicationContext)
    }

    val serviceIntent by lazy {
        Intent(this, PlayerService::class.java)
    }

    private val notificationCreator by lazy {
        appComponent.fetchNotificationCreator()
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() = notificationCreator.createNotifyChannel(
        channelId = Constants.Notification.CHANNEL_ID,
        channelName = Constants.Notification.CHANNEL_NAME,
        priority = NotificationPriority.LOW,
    )
}

fun Context.fetchApp(): MixPlayerApp {
    return applicationContext as MixPlayerApp
}

@Composable
fun fetchAppComponent(): AppComponent {
    return LocalContext.current.fetchApp().appComponent
}
