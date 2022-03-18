apply {
    from("$rootDir/library-build.gradle")
}

plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
}

// optional for specific depedencies only
dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.movieDataSource))
    "implementation"(project(Modules.movieDomain))

    "implementation"(Ktor.ktorClientMock)
    "implementation"(Ktor.clientSerialization)
}