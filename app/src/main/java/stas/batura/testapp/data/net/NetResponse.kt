package stas.batura.testapp.data.net

import com.squareup.moshi.Json

data class NetResponse (
    @Json(name = "a")
    var page: String = "",

    @Json(name = "task_id")
    var perPage: Int = 0,

    @Json(name = "status")
    var total: Int = 0,

    @Json(name = "results")
    var links: Links,
) {

}

data class Links (
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

data class ErrorResponse(
    @Json(name = "error_code")
    var errorCode: Int = 0,

    @Json(name = "error_msg")
    var message: String
)