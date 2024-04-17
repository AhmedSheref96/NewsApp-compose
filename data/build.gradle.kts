plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "com.el3asas.data"
    compileSdk = 34

    android.buildFeatures.buildConfig = true
    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String","BaseUrl","\"https://newsapi.org/v2/\"")
        // TODO: chang it to be in local.properties file when you want to secure your key.
        buildConfigField("String","ApiKey","\"284af891f32f4a00b28febe91bc2e04b\"")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.ksp.gradlePlugin)
    implementation(libs.ksp.api)
    implementation(libs.ksp)

    implementation(libs.retrofit.gson)
    implementation(libs.retrofit)
    implementation(platform(libs.okhttp3.bom))
    implementation(libs.okhttp3.okhttp)
    implementation(libs.okhttp3.logging.interceptor)

    implementation(libs.paging3.runtime)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging3)
    ksp(libs.room.compiler)
    
    implementation(project(":domain"))
}