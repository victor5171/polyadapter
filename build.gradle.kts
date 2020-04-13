apply(plugin = "org.jlleitschuh.gradle.ktlint")
apply(plugin = "org.jlleitschuh.gradle.ktlint-idea")

buildscript {
    apply(from = "versions.gradle.kts")

    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${project.extra["gradleVersion"]}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlinVersion"]}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:${project.extra["ktlintVersion"]}")
        classpath("com.novoda:bintray-release:${project.extra["bintrayReleaseVersion"]}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
