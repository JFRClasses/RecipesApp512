import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.0.21"
    id("com.google.devtools.ksp") version "2.2.20-2.0.4"
    id("de.jensklingenberg.ktorfit") version "2.6.4"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            //Navigation
            implementation(libs.navigation.compose)

            // Icons
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
            // Ktorfit
            implementation("de.jensklingenberg.ktorfit:ktorfit-lib:2.6.4")

            // Ktor Client
            implementation("io.ktor:ktor-client-core:3.3.1")
            implementation("io.ktor:ktor-client-content-negotiation:3.3.1")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.3.1")

            // Coil 3
            implementation("io.coil-kt.coil3:coil-compose:3.1.0")
            implementation("io.coil-kt.coil3:coil-network-ktor3:3.1.0")

            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.pjasoft.recipeapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        // bitebits.carlosoft.com
        applicationId = "com.carlossoft.bitebits"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 202511211
        versionName = "1.0.0" // x.y.z
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

