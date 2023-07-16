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

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    kotlin("kapt")
}

repositories {
    google()
    mavenCentral()
}

android {
    namespace = "ru.aleshin.features.home.impl"
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        minSdk = Config.minSdkVersion

        testInstrumentationRunner = Config.testInstrumentRunner
        consumerProguardFiles(Config.consumerProguardFiles)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Config.kotlinCompiler
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":module_injector"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":features:home:api"))
    implementation(project(":features:player:api"))
    implementation(project(":features:settings:api"))

    implementation(Dependencies.AndroidX.core)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.lifecycleRuntime)
    implementation(Dependencies.AndroidX.material)
    implementation(Dependencies.AndroidX.placeHolder)
    implementation(Dependencies.AndroidX.systemUiController)
    implementation(Dependencies.AndroidX.refresh)

    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.activity)

    implementation(Dependencies.ExoPlayer.library)
    implementation(Dependencies.ExoPlayer.ui)

    implementation(Dependencies.Glide.library)

    implementation(Dependencies.Dagger.core)
    kapt(Dependencies.Dagger.kapt)

    implementation(Dependencies.Voyager.navigator)
    implementation(Dependencies.Voyager.screenModel)
    implementation(Dependencies.Voyager.transitions)

    testImplementation(Dependencies.Test.jUnit)
    testImplementation(Dependencies.Test.turbine)
    androidTestImplementation(Dependencies.Test.jUnitExt)
    androidTestImplementation(Dependencies.Test.espresso)
    androidTestImplementation(Dependencies.Test.composeJUnit)
    debugImplementation(Dependencies.Compose.uiTooling)
    debugImplementation(Dependencies.Compose.uiTestManifest)
}
