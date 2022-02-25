import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()

    maven("https://jitpack.io")
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("dev.icerock.moko:kswift-gradle-plugin:0.4.0")
}