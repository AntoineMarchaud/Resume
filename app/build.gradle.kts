plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.amarchaud.templateresume"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.amarchaud.templateresume"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 2
        versionName = "2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }
}

kotlin {
    jvmToolchain(21)
}

/*
 * Dependencies needed by several configurations are declared only once, in a dedicated
 * configuration that the others extend:
 * - the Compose BOM aligns the version-less Compose artifacts of the app and of its instrumented tests,
 * - kotlin-test is the test API of both local and instrumented tests.
 */
val composeBom: Configuration = configurations.create("composeBom") {
    isCanBeConsumed = false
    isCanBeResolved = false
}
val kotlinTest: Configuration = configurations.create("kotlinTest") {
    isCanBeConsumed = false
    isCanBeResolved = false
}

configurations {
    implementation { extendsFrom(composeBom) }
    testImplementation { extendsFrom(kotlinTest) }
    androidTestImplementation { extendsFrom(composeBom, kotlinTest) }
}

dependencies {
    composeBom(platform(libs.androidx.compose.bom))
    kotlinTest(libs.kotlin.test.junit)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.adaptive)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.kotlinx.io.core)

    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.test.runner)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    constraints {
        androidTestImplementation(libs.androidx.test.espresso.core) {
            because("Espresso < 3.7, pulled by ui-test, calls InputManager.getInstance() which no longer exists on Android 16 QPR2")
        }
    }
}
