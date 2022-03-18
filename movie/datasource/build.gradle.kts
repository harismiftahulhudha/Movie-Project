apply {
    from("$rootDir/library-build.gradle")
}
plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id(SqlDelight.plugin)
}

// optional for specific depedencies only
dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.movieDomain))

    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.logging)
    "implementation"(Ktor.logback)
    "implementation"(Ktor.android)

    "implementation"(SqlDelight.runtime)
}

sqldelight {
    database("MovieDatabase") {
        packageName = "co.harismiftahulhudha.datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}