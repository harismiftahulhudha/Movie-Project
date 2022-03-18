apply {
    from("$rootDir/library-build.gradle")
}

plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
}

// optional for specific depedencies only
dependencies {
    "implementation"(Ktor.clientSerialization)
}