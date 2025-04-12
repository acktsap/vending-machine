# buildSrc

Contains build logic which can be used in subprojects. This is based on official guide [Sharing Build Logic between SubProjects](https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html).

It uses [Convention Plugin](https://docs.gradle.org/current/userguide/custom_plugins.html#sec:convention_plugins) which can be used in build.gradle.kts file of each subprojects.

Just think this as setting up a convention for this project. The directory is organized as

- src/main/kotlin : Actual conventions for the project.
- src/main/kotlin/module : Common fine-grained plugin configurations. Those are used in actual convention defined in src/main/kotlin

Conventions plugin is actually [Pre-compiled Script Plugin](https://docs.gradle.org/current/userguide/implementing_gradle_plugins_precompiled.html). It can be referenced in this way.

```kotlin
/* if convention exists in src/main/kotlin/aaa.some-convention.gradle.kts */
plugins {
  id("aaa.some-convention")
}
```

By organizing plugin configurations in this way, it is easy to manage and share build logic between subprojects.
