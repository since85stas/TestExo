package stas.batura.testapp

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.util.*

@BindingAdapter("userName")
fun TextView.userName(name: String?) {
    text = name
}

@BindingAdapter("loadVisible")
fun ProgressBar.loadVisible(boolean: Boolean) {
    if (boolean) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}





