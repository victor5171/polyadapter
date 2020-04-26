import com.novoda.gradle.release.PublishExtension
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("com.novoda.bintray-release")
}

val currentTag = System.getenv("CIRCLE_TAG") ?: "0.0.1"

configure<PublishExtension> {
    userOrg = "victor5171"
    groupId = "org.xtras.polyadapter"
    repoName = "xtras"
    artifactId = "polyadapter"
    publishVersion = currentTag
    bintrayUser = "victor5171"
    bintrayKey = System.getenv("BINTRAY_KEY")
    desc = "A lightweight polymorphic adapter for RecyclerViews!"
    website = "https://github.com/victor5171/polyadapter"
}

android {
    compileSdkVersion(properties["compileSdkVersion"] as Int)
    buildToolsVersion(properties["buildToolsVersion"] as String)

    defaultConfig {
        minSdkVersion(properties["minSdkVersion"] as Int)
        targetSdkVersion(properties["targetSdkVersion"] as Int)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    implementation("org.jetbrains.kotlin:kotlin-reflect:${KotlinCompilerVersion.VERSION}")
    implementation("androidx.recyclerview:recyclerview:${properties["recyclerViewVersion"]}")
    implementation("androidx.paging:paging-runtime-ktx:${properties["paginationVersion"]}")
    testImplementation("junit:junit:${properties["junit4Version"]}")
    testImplementation("org.robolectric:robolectric:${properties["robolectricVersion"]}")
    testImplementation("androidx.test:core-ktx:${properties["coreKtxTestingVersion"]}")
    testImplementation("io.mockk:mockk:${properties["mockkVersion"]}")
}
