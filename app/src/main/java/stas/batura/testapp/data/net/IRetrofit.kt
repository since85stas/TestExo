package stas.batura.testapp.data.net

import retrofit2.http.*

interface IRetrofit {

    @GET("users")
    suspend fun getPayments(): UsersRequest

}