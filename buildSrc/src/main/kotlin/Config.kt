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

/**
 * @author Stanislav Aleshin on 12.06.2023.
 */
object Config {
    const val applicationId = "ru.aleshin.mixplayer"

    const val compileSdkVersion = 34
    const val targetSdkVersion = 34
    const val minSdkVersion = 24

    const val versionCode = 1
    const val versionName = "0.1-alpha"

    const val testInstrumentRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val consumerProguardFiles = "consumer-rules.pro"

    const val jvmTarget = "17"

    const val kotlinCompiler = "1.4.6"
}
