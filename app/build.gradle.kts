plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.groupe.telnet.carpooling.map"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.groupe.telnet.carpooling.carpooling_app"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kotlin {
        sourceSets {
            debug {
                kotlin.srcDir("build/generated/ksp/debug/kotlin")
            }
            release {
                kotlin.srcDir("build/generated/ksp/release/kotlin")
            }
        }
    }
}

dependencies {
    implementation("org.osmdroid:osmdroid-android:6.1.13")

    implementation("com.github.MKergall:osmbonuspack:6.9.0")
    implementation("com.google.accompanist:accompanist-permissions:0.31.5-beta")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material:1.6.5")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.0.0-alpha05")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))

    //
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.0.5")

    implementation("com.google.accompanist:accompanist-navigation-animation:0.31.2-alpha")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.31.2-alpha")
    implementation("com.google.accompanist:accompanist-permissions:0.31.3-beta")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha10")


//    implementation("io.github.raamcosta.compose-destinations:core:1.5.9-beta")
//    ksp( "io.github.raamcosta.compose-destinations:ksp:1.5.9-beta")

    // Add Retrofit and Gson converter dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("io.reactivex.rxjava3:rxjava:3.0.0")

    //dagger
    // Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    implementation("androidx.appcompat:appcompat:1.6.1")
    ksp("com.google.dagger:hilt-compiler:2.47")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    ksp("androidx.hilt:hilt-compiler:1.0.0")


    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.room:room-ktx:2.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
