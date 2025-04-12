plugins {
    id("acktsap.java-application-jdk21-conventions")
}

application {
    // Define the main class for the application.
    mainClass = "acktsap.vendingmachine.Main"
}

dependencies {
    implementation(libs.slf4j)

    runtimeOnly(libs.log4j.slf4j)

    testImplementation(libs.bundles.test.java)
}
