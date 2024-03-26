import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    implementation("com.android.tools.build:gradle:8.2.1")
    // Required to fix a missing dependency error
    implementation("com.squareup:javapoet:1.13.0")
}


val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "18"
}