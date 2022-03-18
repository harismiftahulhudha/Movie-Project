apply {
    from("$rootDir/library-build.gradle")
}

// optional for specific depedencies only
dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.movieDataSource))
    "implementation"(project(Modules.movieDomain))

    "implementation"(Kotlinx.coroutinesCore)

    "testImplementation"(project(Modules.movieDataSourceTest))
    "testImplementation"(Junit.junit4)
    "testImplementation"(Ktor.ktorClientMock)
    "testImplementation"(Ktor.clientSerialization)
}