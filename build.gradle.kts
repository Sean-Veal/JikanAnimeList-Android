// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    kotlin("jvm") version "2.4.0"
    id("com.google.devtools.ksp") version "2.3.9"
    id("com.google.dagger.hilt.android") version "2.60.1" apply false
}

buildscript {
    configurations.all {
        resolutionStrategy {
            force("org.jetbrains:annotations:23.0.0")
        }
    }
}