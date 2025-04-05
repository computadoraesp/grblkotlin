plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.grblkotlin"
    compileSdk = 35 // Usamos una versión más estable como 35

    defaultConfig {
        applicationId = "com.example.grblkotlin"
        minSdk = 26
        targetSdk = 35 // Mantener en 35 por problemas con versiones inestables
        versionCode = 1
        versionName = "1.0"



        ndk {
            // Asegúrate de especificar las arquitecturas si lo necesitas
            // Si no lo necesitas, puedes comentar o eliminar esta línea.
            //abiFilters("armeabi-v7a", "arm64-v8a")
        }
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildToolsVersion = "35.0.0"
    ndkVersion = "29.0.13113456 rc1"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.appcompat.v131)
    implementation(libs.androidx.constraintlayout.v204)
}
