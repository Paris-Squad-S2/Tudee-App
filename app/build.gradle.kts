plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    id("jacoco")
}

jacoco {
    toolVersion = "0.8.11"
}

android {
    namespace = "com.example.tudeeapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tudeeapp"
        minSdk = 25
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
        debug {
            enableUnitTestCoverage = true
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material)
    implementation(libs.navigation.compose)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Splash Api
    implementation(libs.androidx.core.splashscreen)

    //coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.test)

    //kotlinx date time
    implementation(libs.kotlinx.datetime)

    //kotlinx serialization
    implementation(libs.kotlinx.serialization.json)

    //coil
    implementation(libs.coil.compose)

    //Koin
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.android)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //Lottie
    implementation(libs.lottie.compose)

    // testing
    testImplementation(libs.truth)
    testImplementation(libs.mockk)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.androidx.core)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.core.testing)

    //android testing
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}

val buildDir = layout.buildDirectory.get().asFile

tasks.withType<Test> {
    useJUnitPlatform()

    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
    finalizedBy("jacocoTestReport", "jacocoTestCoverageVerification")
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")
    group = "Reporting"
    description = "Generate Jacoco coverage reports"

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val classDirTree = fileTree("$buildDir/tmp/kotlin-classes/debug") {
        exclude(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*",
            "**/*_Factory*.*",
            "**/*_Impl*.*"
        )
    }

    classDirectories.setFrom(classDirTree)
    sourceDirectories.setFrom(files("$projectDir/src/main/java"))

    executionData.setFrom(fileTree(buildDir) {
        include("jacoco/testDebugUnitTest.exec")
    })
}

tasks.register<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn("testDebugUnitTest")
    group = "Verification"
    description = "Verify code coverage thresholds"

    val classDirTree = fileTree("$buildDir/tmp/kotlin-classes/debug") {
        exclude(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*",
            "**/*_Factory*.*",
            "**/*_Impl*.*"
        )
    }

    classDirectories.setFrom(classDirTree)
    sourceDirectories.setFrom(files("$projectDir/src/main/java"))
    executionData.setFrom(fileTree(buildDir) {
        include("jacoco/testDebugUnitTest.exec")
    })

    violationRules {
        rule {
            limit {
                minimum = "0.8".toBigDecimal()
                counter = "LINE"
                value = "COVEREDRATIO"
            }
        }
    }
}