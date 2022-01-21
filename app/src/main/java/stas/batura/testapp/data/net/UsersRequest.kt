package stas.batura.testapp.data.net

import com.squareup.moshi.Json
import java.util.*

data class UsersRequest (
    @Json(name = "page")
    var page: Int = 0,

    @Json(name = "per_page")
    var perPage: Int = 0,

    @Json(name = "total")
    var total: Int = 0,

    @Json(name = "toatal_pages")
    var totalPages: Int = 0,

    @Json(name = "data")
    var users: List<UserResponse>,

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