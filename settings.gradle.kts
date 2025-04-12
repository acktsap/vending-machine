dependencyResolutionManagement {
    // repositories to fetch dependencies
    repositories {
        mavenCentral()
        mavenLocal() // uses gradle base over maven base ($HOME/.m2)
    }
}

rootProject.name = "vending-machine"

include(
    "application",
)
