package co.harismiftahulhudha.core.domain

sealed class DataState<T> {
    data class Success<T>(
        val data: T? = null
    ): DataState<T>()

    data class Error<T>(
        val uiComponent: UIComponent,
        val data: T? = null
    ): DataState<T>()

    data class Loading<T>(
        val progressBarState: ProgressBarState = ProgressBarState.Idle,
        val data: T? = null
    ): DataState<T>()

    data class Unauthorized<T>(
        val uiComponent: UIComponent
    ): DataState<T>()
}