apply {
    from("$rootDir/android-library-build.gradle")
}

// optional for specific depedencies only
dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.components))
    "implementation"(project(Modules.movieDomain))
    "implementation"(project(Modules.movieInteractors))
    "implementation"(Compose.paging)
    "implementation"(Compose.pagingCompose)
    "testImplementation"(ComposeTest.uiTestPaging)

    "implementation"(Coil.coil)

    "implementation"(Google.gson)

    "implementation"(SqlDelight.androidDriver)

    "androidTestImplementation"(project(Modules.movieDataSourceTest))
    "androidTestImplementation"(ComposeTest.uiTestJunit4)
    "debugImplementation"(ComposeTest.uiTestManifest)
    "androidTestImplementation"(Junit.junit4)
}