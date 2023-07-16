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
package ru.aleshin.core.common.platform.screenmodel.work

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.aleshin.core.common.functional.Either
import ru.aleshin.core.common.managers.CoroutineBlock
import ru.aleshin.core.common.managers.CoroutineManager
import ru.aleshin.core.common.platform.screenmodel.contract.BaseAction
import ru.aleshin.core.common.platform.screenmodel.contract.BaseEvent
import ru.aleshin.core.common.platform.screenmodel.contract.BaseUiEffect
import ru.aleshin.core.common.platform.screenmodel.contract.BaseViewState
import ru.aleshin.core.common.platform.screenmodel.store.BaseStore

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
interface WorkScope<S : BaseViewState, A : BaseAction, F : BaseUiEffect> : WorkResultHandler<A, F> {

    fun launchBackgroundWork(
        key: BackgroundWorkKey,
        scope: CoroutineScope? = null,
        block: CoroutineBlock,
    ): Job

    suspend fun state(): S
    suspend fun sendAction(action: A)
    suspend fun sendEffect(effect: F)

    class Base<S : BaseViewState, E : BaseEvent, A : BaseAction, F : BaseUiEffect>(
        private val store: BaseStore<S, E, A, F>,
        private val coroutineScope: CoroutineScope,
    ) : WorkScope<S, A, F> {

        private val backgroundWorkMap = mutableMapOf<BackgroundWorkKey, Job>()

        override suspend fun state(): S {
            return store.fetchState()
        }

        override suspend fun sendAction(action: A) {
            store.handleAction(action)
        }

        override suspend fun sendEffect(effect: F) {
            store.postEffect(effect)
        }

        override fun launchBackgroundWork(
            key: BackgroundWorkKey,
            scope: CoroutineScope?,
            block: CoroutineBlock,
        ): Job {
            backgroundWorkMap[key].let { job ->
                if (job != null) {
                    job.cancel()
                    backgroundWorkMap.remove(key)
                }
            }
            return (scope ?: coroutineScope).launch { block() }.apply {
                backgroundWorkMap[key] = this
                start()
            }
        }

        override suspend fun Either<A, F>.handleWork() = when (this) {
            is Either.Left -> sendAction(data)
            is Either.Right -> sendEffect(data)
        }

        override suspend fun Flow<Either<A, F>>.collectAndHandleWork() {
            collect { it.handleWork() }
        }
    }
}

interface BackgroundWorkKey
