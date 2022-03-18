apply {
    from("$rootDir/library-build.gradle")
}

// optional for specific depedencies only
dependencies {
    "implementation"(project(Modules.core))
}