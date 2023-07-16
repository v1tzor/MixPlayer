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

package ru.aleshin.mixplayer.presentation.ui.main.viewmodel

import ru.aleshin.core.common.platform.communications.state.EffectCommunicator
import ru.aleshin.mixplayer.presentation.ui.main.contract.MainEffect
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 13.06.2023.
 */
interface MainEffectCommunicator : EffectCommunicator<MainEffect> {

    class Base @Inject constructor() : MainEffectCommunicator,
        EffectCommunicator.Abstract<MainEffect>()
}
