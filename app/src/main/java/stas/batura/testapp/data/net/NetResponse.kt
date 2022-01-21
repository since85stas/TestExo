package stas.batura.testapp.data.net

import com.squareup.moshi.Json
import java.util.*

data class NetResponse (
    @Json(name = "a")
    var page: String = "",

    @Json(name = "task_id")
    var perPage: Int = 0,

    @Json(name = "status")
    var total: Int = 0,

    @Json(name = "results")
    var videos: Videos,
) {

}

data class Videos (
    @Json(name = "src")
    var src: String = "",

    @Json(name = "single")
    var single: String = "",

    @Json(name = "split_v")
    var splitV: String = "",

    @Json(name = "split_h")
    var splitH: String = "",

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