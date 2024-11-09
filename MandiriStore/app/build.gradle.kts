plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services") version "4.4.2" apply false
}

android {
    namespace = "com.banihasanmaulid.mandiristore"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.banihasanmaulid.mandiristore"
        minSdk = 21
        targetSdk = 34
        versionCode = generateVersionCode()
        versionName = generateVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
    }

    flavorDimensions += mutableListOf("environment")
    productFlavors {
        create("development") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://fakestoreapi.com/\"")
        }
        create("staging") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://staging-api.fakestoreapi.com/\"")
        }
        create("production") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://fakestoreapi.com/\"")
        }
    }
}

private fun generateVersionCode(): Int {
    val versionMajor = if (getVersionMajor().isNotEmpty()) getVersionMajor().toInt() else 0
    val versionMinor = if (getVersionMinor().isNotEmpty()) getVersionMinor().toInt() else 0
    val versionPatch = if (getVersionPatch().isNotEmpty()) getVersionPatch().toInt() else 0

    return versionMajor * 1000000 + versionMinor * 10000 + versionPatch
}

private fun generateVersionName(): String {
    val versionName = project.findProperty("versionName")?.toString()?: "Mandiri-Store"
    val versionMajor = if (getVersionMajor().isNotEmpty()) "-v" + getVersionMajor() else ""
    val versionMinor = if (getVersionMinor().isNotEmpty()) "." + getVersionMinor() else ""
    val versionPatch = if (getVersionPatch().isNotEmpty()) "." + getVersionPatch() else ""

    val versionCode = String.format("%s%s%s",
        versionMajor,
        versionMinor,
        versionPatch
    )

    return String.format("%s%s", versionName, versionCode)
}


private fun getVersionMajor(): String {
    return project.findProperty("versionMajor")?.toString()?: ""
}

private fun getVersionMinor(): String {
    return project.findProperty("versionMinor")?.toString()?: ""
}

private fun getVersionPatch(): String {
    return project.findProperty("versionPatch")?.toString()?: ""
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.dagger:dagger:2.42")
    annotationProcessor("com.google.dagger:dagger-compiler:2.42")
    implementation("androidx.room:room-runtime:2.5.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}