plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "ir.codroid.tracker_data"
}
dependencies {
    with(Modules) {
        "implementation"(project(core))
        "implementation"(project(trackerDomain))
    }
    with(Retrofit) {
        "implementation"(okHttp)
        "implementation"(retrofit)
        "implementation"(okHttpLoggingInterceptor)
        "implementation"(moshiConverter)
    }
    with(Room) {
        "kapt"(roomCompiler)
        "implementation"(roomKtx)
        "implementation"(roomRuntime)
    }
}
