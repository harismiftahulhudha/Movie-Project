package co.harismiftahulhudha.core.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseListResponse<T>(
    @SerialName("results")
    val results: T
)
