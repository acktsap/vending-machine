package module

plugins {
    // java itself
    // https://docs.gradle.org/current/userguide/java_plugin.html
    java

    // for managing custom test suite
    // https://docs.gradle.org/current/userguide/jvm_test_suite_plugin.html
    `jvm-test-suite`

    // coverage
    // https://docs.gradle.org/current/userguide/jacoco_plugin.html
    jacoco

    // for lint
    // https://docs.gradle.org/current/userguide/checkstyle_plugin.html
    checkstyle

    // apply lombok to project
    // https://docs.freefair.io/gradle-plugins/8.10.2/reference/
    id("io.freefair.lombok")
}


/* java */

java {
    toolchain {
        // uses common toolchain
        // it automatically sets sourceCompatibility & targetCompatibility to matching version
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.jar {
    // customizing manifest file
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
            ),
        )
    }
}

tasks.compileJava { // both for compileJava & compileTestJava
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters") // for parameter name injection in spring
    options.compilerArgs.add("-Xlint:deprecation") // show usage of deprecated code
}

tasks.javadoc {
    options {
        (this as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet")
    }
}

testing {
    suites {
        val commonTestJvmOptions = listOf(
            "--add-opens", "java.base/java.lang=ALL-UNNAMED",
            "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED",
            "--add-opens", "java.base/java.lang.invoke=ALL-UNNAMED",
            "--add-opens", "java.base/java.math=ALL-UNNAMED",
            "--add-opens", "java.base/java.net=ALL-UNNAMED",
            "--add-opens", "java.base/java.nio=ALL-UNNAMED",
            "--add-opens", "java.base/java.util=ALL-UNNAMED",
            "--add-opens", "java.base/java.util.stream=ALL-UNNAMED",
            "--add-opens", "java.base/java.io=ALL-UNNAMED",
            "--add-opens", "java.xml/jdk.xml.internal=ALL-UNNAMED",
        )

        // reference to test task which is automatically created
        val test by getting(JvmTestSuite::class) {
            targets {
                all {
                    testTask.configure {
                        useJUnitPlatform()
                        maxParallelForks = Runtime.getRuntime().availableProcessors()

                        jvmArgs(commonTestJvmOptions)
                    }
                }
            }
        }

        register<JvmTestSuite>("integrationTest") {
            dependencies {
                // add dependency of project itself
                implementation(project())
            }

            targets {
                all {
                    testTask.configure {
                        // run after 'test' task
                        shouldRunAfter(test)

                        useJUnitPlatform()
                        maxParallelForks = Runtime.getRuntime().availableProcessors()

                        jvmArgs(commonTestJvmOptions)
                    }
                }
            }
        }
    }
}

// configure dependencies integrationTest from project itself
// see also: https://github.com/gradle/gradle/issues/19870
val integrationTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get(), configurations.testImplementation.get())
}
val integrationTestRuntimeOnly: Configuration by configurations.getting {
    extendsFrom(configurations.runtimeOnly.get(), configurations.testRuntimeOnly.get())
}


/* jacoco */

jacoco {
    toolVersion = "0.8.12"
    reportsDirectory = layout.buildDirectory.dir("reports/jacoco")
}

tasks.jacocoTestReport {
    dependsOn(tasks.withType<Test>())

    // set execution data from all `.exec` files
    executionData.setFrom(files(project.fileTree("build/jacoco").include("*.exec")))

    reports {
        html.required = true
        xml.required = true
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("jacoco/html")
        xml.outputLocation = layout.buildDirectory.file("jacoco/result.xml")
    }

    doLast {
        val indexFile = project.layout.buildDirectory.dir("jacoco/html").get().file("index.html")
        println("jacoco html report is generated to $indexFile")
    }
}

tasks.jacocoTestCoverageVerification {
    enabled = false // disabled in subproject
}


/* checkstyle */

tasks.withType<Checkstyle>().configureEach {
    reports {
        configFile = file("${project.rootDir}/buildSrc/config/checkstyle.xml")
        configProperties =
            mapOf(
                "suppressionFile" to file("${project.rootDir}/buildSrc/config/checkstyle-suppressions.xml"),
            )
        xml.required.set(false)
        html.required.set(true)
    }
}
