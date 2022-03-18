package co.harismiftahulhudha.datasource_test.network

import co.harismiftahulhudha.datasource.network.MovieService
import co.harismiftahulhudha.datasource.network.MovieServiceImpl
import co.harismiftahulhudha.datasource_test.network.data.MovieDataEmpty
import co.harismiftahulhudha.datasource_test.network.data.MovieDataInvalid
import co.harismiftahulhudha.datasource_test.network.data.MovieDataUnauthorized
import co.harismiftahulhudha.datasource_test.network.data.MovieDataValid
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*

class MovieServiceFake {
    companion object Factory {

        private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
        private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

        fun build(
            type: MovieServiceResponseType
        ): MovieService {
            val client = HttpClient(MockEngine) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(
                        kotlinx.serialization.json.Json {
                            ignoreUnknownKeys = true
                        }
                    )
                }
                engine {
                    addHandler { request ->
                        when (request.url.fullUrl) {
                            "https://api.themoviedb.org/3/movie/popular?page=1" -> {
                                val responseHeaders = headersOf(
                                    "Content-Type" to listOf("application/json", "charset=utf-8")
                                )
                                when(type){
                                    is MovieServiceResponseType.Emptylist -> {
                                        respond(
                                            MovieDataEmpty.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }
                                    is MovieServiceResponseType.InvalidData -> {
                                        respond(
                                            MovieDataInvalid.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }
                                    is MovieServiceResponseType.ValidData -> {
                                        respond(
                                            MovieDataValid.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }
                                    is MovieServiceResponseType.Http401 -> {
                                        respond(
                                            MovieDataUnauthorized.data,
                                            status = HttpStatusCode.Unauthorized,
                                            headers = responseHeaders
                                        )
                                    }
                                }
                            }
                            else -> error("Unhandled ${request.url.fullUrl}")
                        }
                    }
                }
            }
            return MovieServiceImpl(client)
        }
    }
}