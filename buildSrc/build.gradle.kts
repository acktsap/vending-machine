plugins {
    // kotlin dsl versions: https://docs.gradle.org/current/userguide/compatibility.html
    `kotlin-dsl` // support convention plugins in kotlin
}

// repositories to fetch plugin dependencies
repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal() // give accees to gradle community plugins
}

/*
    Dependencies for external plugin.
    When using convention plugin, dependency version of external plugin should be here.
    You can full architect name in gradle official portal https://plugins.gradle.org
 */
dependencies {
    // java
    implementation("io.freefair.gradle:lombok-plugin:8.11")

    // fat jar
    implementation("com.gradleup.shadow:shadow-gradle-plugin:8.3.5")
}
