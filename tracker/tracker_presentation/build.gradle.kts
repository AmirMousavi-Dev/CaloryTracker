plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "ir.codroid.tracker_presentation"
}
dependencies {
    with(Modules) {
        implementation(project(core))
        implementation(project(coreUi))
        implementation(project(trackerDomain))
    }
    with(Coil) {
        implementation(coilCompose)
    }
}
