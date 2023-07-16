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
package ru.aleshin.core.common.functional

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
object Constants {

    object Notification {
        const val CHANNEL_ID = "mixPlayerAlarmChannel"
        const val CHANNEL_NAME = "MixPlayer Service"
        const val FOREGROUND_NOTIFY_ID = 209567821
        const val ACTION_PLAY_PAUSE = "PLAYER_ACTION_PLAY_PAUSE"
        const val ACTION_NEXT = "PLAYER_ACTION_NEXT_TRACK"
        const val ACTION_PREVIOUS = "PLAYER_ACTION_PREVIOUS_TRACK"
    }

    object Delay {
        const val SLIDER_POSITION_UPDATE = 500L
        const val AUTH = 500L
        const val LOAD_ANIMATION = 200L
        const val LOAD_ANIMATION_LONG = 400L
        const val SPLASH = 2000L
        const val SPLASH_TEXT = 800L
        const val CHECK_STATUS = 5000L
        const val SETUP = 200L
    }

    object Date {
        const val DAY = 1
        const val DAYS_IN_WEEK = 7
        const val DAYS_IN_MONTH = 31
        const val DAYS_IN_HALF_YEAR = 183
        const val DAYS_IN_YEAR = 365

        const val EMPTY_DURATION = 0L
        const val MILLIS_IN_SECONDS = 1000L
        const val MILLIS_IN_MINUTE = 60000L
        const val MILLIS_IN_HOUR = 3600000L
        const val SECONDS_IN_MINUTE = 60L
        const val MINUTES_IN_MILLIS = 60000L
        const val MINUTES_IN_HOUR = 60L
        const val HOURS_IN_DAY = 24L

        const val minutesFormat = "%s%s"
        const val hoursFormat = "%s%s"
        const val hoursAndMinutesFormat = "%s%s %s%s"
        const val secondsAndMinutesFormat = "%s:%s"
    }
}
