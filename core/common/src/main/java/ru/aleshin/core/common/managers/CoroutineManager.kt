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
 * limitations under the License.
*/
package ru.aleshin.core.common.managers

import kotlinx.coroutines.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
interface CoroutineManager {

    suspend fun <T> Flow<T>.collectOnBackground(collector: FlowCollector<T>)

    fun runOnBackground(scope: CoroutineScope, block: CoroutineBlock): Job

    fun runOnUi(scope: CoroutineScope, block: CoroutineBlock): Job

    suspend fun changeFlow(coroutineFlow: CoroutineFlow, block: CoroutineBlock)

    abstract class Abstract(
        private val backgroundDispatcher: CoroutineDispatcher,
        private val uiDispatcher: CoroutineDispatcher,
    ) : CoroutineManager {

        override suspend fun <T> Flow<T>.collectOnBackground(collector: FlowCollector<T>) {
            this.flowOn(backgroundDispatcher).collect(collector)
        }

        override fun runOnBackground(scope: CoroutineScope, block: CoroutineBlock): Job {
            return scope.launch(context = backgroundDispatcher, block = block)
        }

        override fun runOnUi(scope: CoroutineScope, block: CoroutineBlock): Job {
            return scope.launch(context = uiDispatcher, block = block)
        }

        override suspend fun changeFlow(coroutineFlow: CoroutineFlow, block: CoroutineBlock) {
            val dispatcher = when (coroutineFlow) {
                CoroutineFlow.BACKGROUND -> backgroundDispatcher
                CoroutineFlow.UI -> uiDispatcher
            }
            withContext(context = dispatcher, block = block)
        }
    }

    class Base @Inject constructor() : Abstract(
        backgroundDispatcher = Dispatchers.IO,
        uiDispatcher = Dispatchers.Main,
    )
}

typealias CoroutineBlock = suspend CoroutineScope.() -> Unit

enum class CoroutineFlow {
    BACKGROUND, UI
}
