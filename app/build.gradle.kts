plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.composeCompiler)
//    alias("com.google.firebase.crashlytics")
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlin.serialization)
}
    android {
        namespace = "com.example.skindiseaseapp"
        compileSdk = 34

        defaultConfig {
            applicationId = "com.example.skindiseaseapp"
            minSdk = 24
            targetSdk = 34
            versionCode = 1
            versionName = "1.0"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }
        buildTypes {
            getByName("release") {
//            buildConfigField ("String", "RELEASE_API_KEY", releaseApiKey)
                isMinifyEnabled = true
                isDebuggable = false
                isShrinkResources = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            getByName("debug") {
//            buildConfigField ("String", "RELEASE_API_KEY", releaseApiKey)
                isMinifyEnabled = false
                isShrinkResources = false
                isDebuggable = true
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        kotlinOptions {
            jvmTarget = "17"
        }
        buildFeatures {
            compose = true
            buildConfig = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.1"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.constraintlayout.compose)
        implementation(libs.androidx.foundation)

        // Navigation Compose
        implementation(libs.navigation.compose)
        implementation(libs.coil.compose)
        implementation(libs.androidx.core.splashscreen)

        implementation(libs.androidx.navigation.fragment.ktx)
        implementation(libs.androidx.navigation.ui.ktx)
        implementation(libs.sdp.ssp.compose.multiplatform)

        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)

        implementation(libs.gson)

        // Landscapist
        implementation(libs.landscapist.coil)
        implementation(libs.landscapist.placeholder)
        implementation(libs.landscapist.animation)

        // Hilt
        implementation(libs.hilt.android)
        implementation(libs.androidx.hilt.navigation.compose)
        ksp(libs.hilt.compiler)

        // Firebase
        implementation(libs.play.services.auth)

        implementation(platform(libs.firebase.bom))
        implementation(libs.firebase.auth.ktx)
        implementation(libs.firebase.firestore.ktx)
        implementation(libs.firebase.storage.ktx)

        // Serialization for type safe-navigation
        implementation(libs.kotlinx.serialization.json)

        implementation(libs.androidx.datastore.preferences)
        implementation(libs.accompanist.permissions)

        // Tensorflow
        implementation(libs.tensorflowLite)

        // Room
        implementation(libs.roomRuntime)
        ksp(libs.roomCompiler)
        implementation(libs.roomKtx)
        implementation(libs.roomKtx)

        // CameraX
        implementation(libs.camera.core)
        implementation(libs.camera.camera2)
        implementation(libs.camera.lifecycle)
        implementation(libs.camera.view)
        implementation(libs.camera.extensions)


        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
    }