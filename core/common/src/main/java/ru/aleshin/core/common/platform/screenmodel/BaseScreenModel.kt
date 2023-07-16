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
package ru.aleshin.core.common.platform.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.FlowCollector
import ru.aleshin.core.common.managers.CoroutineManager
import ru.aleshin.core.common.platform.communications.state.EffectCommunicator
import ru.aleshin.core.common.platform.communications.state.StateCommunicator
import ru.aleshin.core.common.platform.screenmodel.contract.BaseAction
import ru.aleshin.core.common.platform.screenmodel.contract.BaseEvent
import ru.aleshin.core.common.platform.screenmodel.contract.BaseUiEffect
import ru.aleshin.core.common.platform.screenmodel.contract.BaseViewState
import ru.aleshin.core.common.platform.screenmodel.store.launchedStore
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
abstract class BaseScreenModel<S : BaseViewState, E : BaseEvent, A : BaseAction, F : BaseUiEffect>(
    protected val stateCommunicator: StateCommunicator<S>,
    protected val effectCommunicator: EffectCommunicator<F>,
    coroutineManager: CoroutineManager,
) : ScreenModel, Reducer<S, A>, Actor<S, E, A, F>, ContractProvider<S, E, F> {

    private val scope get() = coroutineScope

    protected var isInitialize = AtomicBoolean(false)

    private val store = launchedStore(
        scope = scope,
        effectCommunicator = effectCommunicator,
        stateCommunicator = stateCommunicator,
        actor = this,
        reducer = this,
        coroutineManager = coroutineManager,
    )

    override fun init() {
        isInitialize.set(true)
    }

    override fun dispatchEvent(event: E) = store.sendEvent(event)

    override suspend fun collectState(collector: FlowCollector<S>) {
        store.collectState(collector)
    }

    override suspend fun collectUiEffect(collector: FlowCollector<F>) {
        store.collectUiEffect(collector)
    }
}
