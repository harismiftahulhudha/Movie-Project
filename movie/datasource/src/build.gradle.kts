import java.util.*
import java.io.*
apply {
    from("$rootDir/library-build.gradle")
}
plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id(SqlDelight.plugin)
}

val localProperties = java.util.Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "local.properties")))
}

// optional for specific depedencies only
dependencies {
//    "implementation"(project(Modules.movieDomain))

    "implementation"(project(Modules.core))
    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)

    "implementation"(SqlDelight.runtime)
}

sqldelight {
    database("HeroDatabase") {
        packageName = "co.harismiftahulhudha.datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}