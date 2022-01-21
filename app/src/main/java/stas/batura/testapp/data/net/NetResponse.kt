package stas.batura.testapp.data.net

import com.squareup.moshi.Json
import java.util.*

data class NetResponse (
    @Json(name = "src")
    var page: Int = 0,

    @Json(name = "single")
    var perPage: Int = 0,

    @Json(name = "split_v")
    var total: Int = 0,

    @Json(name = "split_v")
    var totalPages: Int = 0,

){

}


data class UserResponse (
    @Json(name = "id")
    var userId: Int = 0,

    @Json(name= "email")
    var email: String? = "",

    @Json(name = "first_name")
    var firstName: String? = "",

    @Json(name = "last_name")
    var lastName: String? = "",

    @Json(name = "avatar")
    var avatUrl: String? = ""
)

data class ErrorResponse(
    @Json(name = "error_code")
    var errorCode: Int = 0,

    @Json(name = "error_msg")
    var message: String
)