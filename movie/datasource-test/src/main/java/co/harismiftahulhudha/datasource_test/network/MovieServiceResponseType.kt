package co.harismiftahulhudha.datasource_test.network

sealed class MovieServiceResponseType {
    object Emptylist: MovieServiceResponseType()
    object InvalidData: MovieServiceResponseType()
    object ValidData: MovieServiceResponseType()
    object Http401: MovieServiceResponseType()
}
