package module

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    // to make fat-jar
    // https://gradleup.com/shadow/introduction/
    id("com.gradleup.shadow")
}

tasks.named<ShadowJar>("shadowJar") {
    // custom names
    archiveBaseName.set(project.name)
    archiveClassifier.set("all")
    // archiveVersion.set("") // if you want to customize version

    // remove all classes of dependencies that are not used by the project
    // note that it would break the ServiceProvider
    // minimize()

    doLast {
        val shadowJar = fileTree(project.layout.buildDirectory.get().dir("libs"))
            .matching { include("**/*all.jar") }
            .files.first()
        println("Shadow jar is generated to $shadowJar")
    }
}
