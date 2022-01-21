package stas.batura.testapp.data.net

import retrofit2.http.*

interface IRetrofit {

    @GET("test/item")
    suspend fun getData(): NetResponse

}