package kg.hackathon.template.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun convertToBase64(byteArray: ByteArray): String {
    return Base64.encodeToString(byteArray, Base64.NO_WRAP)
}

fun String?.convertBase64ToBitmap(): Bitmap {
    val decodedString: ByteArray = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}