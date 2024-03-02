plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "ir.codroid.calorytracker"
    compileSdk = ProjectConfig.compileSdk


    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    with(Compose) {
        implementation(compiler)
        implementation(ui)
        implementation(uiToolingPreview)
        implementation(hiltNavigationCompose)
        implementation(material)
        implementation(runtime)
        implementation(navigation)
        implementation(viewModelCompose)
        implementation(activityCompose)
    }
    with(DaggerHilt) {
        implementation(hiltAndroid)
        kapt(hiltCompiler)
    }
    with(Modules) {
        implementation(project(core))
        implementation(project(onboardingPresentation))
        implementation(project(onboardingDomain))
        implementation(project(trackerPresentation))
        implementation(project(trackerDomain))
        implementation(project(trackerData))
    }
    with(AndroidX) {
        implementation(coreKtx)
        implementation(appCompat)
    }
    with(Coil) {
        implementation(coilCompose)
    }

    with(Google) {
        implementation(material)
    }

    with(Retrofit) {
    implementation(okHttp)
    implementation(retrofit)
    implementation(okHttpLoggingInterceptor)
    implementation(moshiConverter)
    }
    with(Room) {
    kapt(roomCompiler)
    implementation(roomKtx)
    implementation(roomRuntime)
    }
    with(Testing) {

    testImplementation(junit4)
    testImplementation(junitAndroidExt)
    testImplementation(truth)
    testImplementation(coroutines)
    testImplementation(turbine)
    testImplementation(composeUiTest)
    testImplementation(mockk)
    testImplementation(mockWebServer)

    androidTestImplementation(hiltTesting)
    kaptAndroidTest(DaggerHilt.hiltCompiler)
    androidTestImplementation(testRunner)
    }
}