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

package ru.aleshin.features.player.api.presentation.common

import kotlinx.coroutines.flow.FlowCollector
import ru.aleshin.core.common.functional.MediaCommand
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 14.07.2023.
 */
interface MediaController {

    suspend fun collectCommands(collector: FlowCollector<MediaCommand>)
    fun work(command: MediaCommand)

    class Base @Inject constructor(
        private val communicator: MediaCommunicator
    ) : MediaController {

        override fun work(command: MediaCommand) = communicator.update(command)

        override suspend fun collectCommands(collector: FlowCollector<MediaCommand>) = communicator.collect(collector)
    }
}