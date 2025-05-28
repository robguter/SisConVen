plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Kotlin serialization plugin for type safe routes and navigation arguments
    kotlin("plugin.serialization") version "2.0.21"
    //id("com.google.devtools.ksp")
}

android {
    namespace = "com.sisterag.sisconven"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sisterag.sisconven"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    //dataBinding.enable = true
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    packagingOptions {
        resources {
            excludes += "**/*"
        }
    }


}

dependencies {
    val lcrk = "2.8.7"
    val nfk = "2.8.9"
    val csr = "2.11.0"
    val csoli = "4.12.0"
    val kCA = "1.8.1"
    val kCC = "1.7.1"
    val kSl = "2.1.20"
    val kRf = "1.9.24"
    val pSAI = "18.2.0"
    val pSM = "19.1.0"
    val anc = "2.9.0"
    val gld = "4.16.0"
    val ktor = "2.3.12"

    // Client
    /*implementation("io.ktor:ktor-client-core:$ktor")
    implementation("io.ktor:ktor-client-android:$ktor")*/

    // Json Parsing
    /*implementation("io.ktor:ktor-client-content-negotiation:$ktor")
    implementation("io.ktor:ktor-serialization-kotlinx:$ktor")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$ktor")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor")*/

    // Logging
    /*implementation("ch.gos.logback:logback-classic:1.5.6")*/

    implementation(libs.firebase.appdistribution.gradle)
    implementation(libs.generativeai)
    implementation(libs.firebase.vertexai)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.androidx.constraintlayout)
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lcrk")
    implementation("androidx.navigation:navigation-fragment-ktx:$nfk")
    implementation("androidx.navigation:navigation-ui-ktx:$nfk")
    implementation("androidx.navigation:navigation-compose:$anc")
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:$csr")
    // gson converter
    implementation("com.squareup.retrofit2:converter-gson:$csr")
    // okhttp3
    implementation("com.squareup.okhttp3:logging-interceptor:$csoli")
    implementation("com.squareup.okhttp3:okhttp:$csoli")
    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kCA")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kCC")

    //ksp("com.github.bumptech.glide:compiler:$gld")
    implementation("com.github.bumptech.glide:glide:$gld")
    annotationProcessor("com.github.bumptech.glide:compiler:$gld")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kSl")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kRf")
    implementation("com.google.android.gms:play-services-analytics-impl:$pSAI")
    implementation("com.google.android.gms:play-services-maps:$pSM")
    implementation("androidx.datastore:datastore-preferences:1.1.6")
    implementation("io.coil-kt:coil:2.5.0")
    implementation("com.karumi:dexter:6.2.2")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}