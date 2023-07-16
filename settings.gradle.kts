pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "MixPlayer"

include(":app")
include(":module_injector")
include(":core:common")
include(":core:ui")
include(":features:settings:impl")
include(":features:settings:api")
include(":features:home:impl")
include(":features:home:api")
include(":features:player:impl")
include(":features:player:api")
