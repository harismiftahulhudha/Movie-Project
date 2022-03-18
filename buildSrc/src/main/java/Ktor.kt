object Ktor {
    private const val ktorVersion = "1.6.7"
    private const val logbackVersion = "1.2.11"
    const val core = "io.ktor:ktor-client-core:$ktorVersion"
    const val clientSerialization = "io.ktor:ktor-client-serialization:$ktorVersion"
    const val android = "io.ktor:ktor-client-android:$ktorVersion"
    const val gson = "io.ktor:ktor-client-gson:$ktorVersion"

    const val ktorClientMock = "io.ktor:ktor-client-mock:$ktorVersion"

    const val logging = "io.ktor:ktor-client-logging:$ktorVersion"
    const val logback = "ch.qos.logback:logback-classic:$logbackVersion"
}