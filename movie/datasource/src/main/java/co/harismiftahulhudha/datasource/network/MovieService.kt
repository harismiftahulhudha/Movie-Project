package co.harismiftahulhudha.datasource.network

import co.harismiftahulhudha.domain.Movie
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.statement.*
import io.ktor.http.*

interface MovieService {
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun detailMovie(id: Long): Movie
    companion object Factory {
        fun build(): MovieService {
            return MovieServiceImpl(
                httpClient = HttpClient(Android) {
                    HttpResponseValidator {
                        handleResponseException { exception ->
                            val clientException = exception as? ClientRequestException ?: return@handleResponseException
                            val exceptionResponse = clientException.response
                            val exceptionResponseText = exceptionResponse.readText()
                            throw Exception(exceptionResponseText)
                        }
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(
                            kotlinx.serialization.json.Json {
                                ignoreUnknownKeys = true // if the server returns extra fields, ignore them
                            }
                        )
                    }
                    install(Logging) {
                        logger = Logger.DEFAULT
                        level = LogLevel.BODY
                    }
                }
            )
        }
    }
}