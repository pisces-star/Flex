plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.pisces.litho"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    api(project(":core"))

    implementation ("com.facebook.yoga:yoga:2.0.0")
    implementation ("com.facebook.yoga:proguard-annotations:1.19.0")
    implementation ("com.facebook.fresco:fresco:3.1.3")

    // Litho
    implementation ("com.facebook.litho:litho-core:0.48.0")
    api ("com.facebook.litho:litho-core-kotlin:0.48.0")
    api ("com.facebook.litho:litho-widget:0.48.0")
    api ("com.facebook.litho:litho-widget-kotlin:0.48.0")
    implementation("com.facebook.litho:litho-fresco:0.48.0")
    kapt ("com.facebook.litho:litho-processor:0.48.0")
    // SoLoader
    implementation ("com.facebook.soloader:soloader:0.10.5")
    // Testing Litho
    testImplementation ("com.facebook.litho:litho-testing:0.48.0")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}