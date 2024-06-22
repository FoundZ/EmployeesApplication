plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.takumi.test.app"
    compileSdk = 34
    targetProjectPath = ":app"

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "com.takumi.test.app.runner.HiltTestRunner"
    }

    buildFeatures {
        aidl = false
        buildConfig = false
        renderScript = false
        shaders = false
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
// Allow references to generated code
kapt {
    correctErrorTypes = true
    arguments {
        // Make Hilt share the same definition of Components in tests instead of
        // creating a new set of Components per test class.
        arg("dagger.hilt.shareTestComponents", "true")
    }
}

dependencies {

    implementation(project(":app"))
    implementation(project(":employee-data"))
    implementation(project(":employee-ui"))
    implementation(project(":employee-biz"))

    // Hilt and instrumented tests.
    implementation(libs.hilt.android.testing)
    kapt(libs.hilt.android.compiler)

    // Testing
    implementation(libs.junit)
    implementation(libs.androidx.test.runner)
    implementation(libs.kotlinx.coroutines.test)

    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.espresso.contrib)
    implementation(libs.androidx.espresso.intents)

    implementation(libs.androidx.test.core.ktx)
    implementation(libs.androidx.test.ext.junit.ktx)
    implementation(libs.androidx.test.rules)


}