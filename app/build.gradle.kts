plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.fghilmany.gofoodclone"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.fghilmany.gofoodclone"
        minSdk = 24
        targetSdk = 33
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // login module
    implementation(project(":login:domain"))
    implementation(project(":login:http"))

    // register module
    implementation(project(":register:domain"))
    implementation(project(":register:http"))
    implementation(project(":register:presentation"))

    // preference module
    implementation(project(":preference:domain"))
    implementation(project(":preference:cache"))

    // common module
    implementation(project(":common"))

    implementation(libs.android.ktx)
    implementation(libs.appCompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espressoCore)

    implementation(libs.lifecycle.viemodel.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    implementation(libs.retrofit)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.testingCore)

    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.turbine)

}