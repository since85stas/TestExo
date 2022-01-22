package stas.batura.testapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import stas.batura.testapp.data.net.UserResponse

@Entity(tableName = "video_table")
data class Video (

    @PrimaryKey()
    var id: Int = 0,

    var url: String? = "",

){

}





