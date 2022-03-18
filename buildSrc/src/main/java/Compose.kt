object Compose {
    private const val activityComposeVersion = "1.4.0"
    const val activity = "androidx.activity:activity-compose:$activityComposeVersion"

    const val composeVersion = "1.1.1"
    const val ui = "androidx.compose.ui:ui:$composeVersion"
    const val material = "androidx.compose.material:material:$composeVersion"
    const val tooling = "androidx.compose.ui:ui-tooling:$composeVersion"

    private const val navigationVersion = "2.4.1"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"

    private const val hiltNavigationComposeVersion = "1.0.0"
    const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"

    const val pagingVersion = "3.1.0"
    const val paging = "androidx.paging:paging-runtime:$pagingVersion"

    private const val pagingComposeVersion = "1.0.0-alpha14"
    const val pagingCompose = "androidx.paging:paging-compose:$pagingComposeVersion"


}

object ComposeTest {
    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Compose.composeVersion}"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Compose.composeVersion}"
    const val uiTestPaging = "androidx.paging:paging-common:${Compose.pagingVersion}"
}