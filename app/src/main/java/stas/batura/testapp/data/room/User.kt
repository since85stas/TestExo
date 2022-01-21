package stas.batura.testapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import stas.batura.testapp.data.net.UserResponse

@Entity(tableName = "users_table")
data class User (

    @PrimaryKey()
    var userId: Int = 0,

    var email: String? = "",

    var firstName: String? = "",

    var lastName: String? = "",

    var avatUrl: String? = ""
){

    object FromUserResponse {

        fun build(respons: UserResponse): User {

            return User(
                userId = respons.userId,
                email = respons.email,
                firstName = respons.firstName,
                lastName = respons.lastName,
                avatUrl = respons.avatUrl
            )
        }

    }
}

class CategoryDataConverter {

    @TypeConverter()
    fun fromCountryLangList(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCountryLangList(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

}




