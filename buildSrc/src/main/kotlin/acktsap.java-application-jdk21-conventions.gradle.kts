plugins {
    id("module.acktsap.application") // need to set main class
    id("module.acktsap.fatjar")
    id("module.acktsap.java")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
