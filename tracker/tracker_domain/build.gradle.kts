plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "ir.codroid.tracker_domain"
}
dependencies {
    with(Modules) {
        implementation(project(core))
        implementation(Coroutines.coroutines)
    }
}
