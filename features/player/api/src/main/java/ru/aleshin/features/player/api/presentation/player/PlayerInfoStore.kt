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

package ru.aleshin.features.player.api.presentation.player

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.aleshin.core.common.functional.audio.PlayerError
import ru.aleshin.core.common.functional.audio.PlayerInfo
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 13.07.2023.
 */
interface PlayerInfoStore : PlayerInfoStoreCollect {

    fun fetchInfo(): PlayerInfo
    fun updateInfo(builder: PlayerInfo.() -> PlayerInfo)
    fun sendError(error: PlayerError)

    class Base @Inject constructor(
        private val infoFlow: MutableStateFlow<PlayerInfo> = MutableStateFlow(PlayerInfo()),
        private val errorsFlow: MutableSharedFlow<PlayerError> = MutableSharedFlow(extraBufferCapacity = 1),
    ) : PlayerInfoStore {

        private var info = PlayerInfo()

        override fun fetchInfo(): PlayerInfo {
            return info
        }

        override fun updateInfo(builder: PlayerInfo.() -> PlayerInfo) {
            info = builder(info).apply { infoFlow.tryEmit(this) }
        }

        override fun sendError(error: PlayerError) {
            errorsFlow.tryEmit(error)
        }

        override suspend fun collectInfo(collector: FlowCollector<PlayerInfo>) = infoFlow.collect(collector)

        override suspend fun collectErrors(collector: FlowCollector<PlayerError>) = errorsFlow.collect(collector)
    }
}

interface PlayerInfoStoreCollect {
    suspend fun collectInfo(collector: FlowCollector<PlayerInfo>)
    suspend fun collectErrors(collector: FlowCollector<PlayerError>)
}