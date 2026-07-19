plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.androidstudydemo"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        aidl = true
    }

    defaultConfig {
        applicationId = "com.example.androidstudydemo"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    implementation("com.github.GrenderG:Toasty:1.5.2")
    implementation("com.github.bumptech.glide:glide:5.0.7")
    implementation("com.airbnb.android:lottie:6.4.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.guolindev.permissionx:permissionx:1.8.0")
    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.1.0")
}
