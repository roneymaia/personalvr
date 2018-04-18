package net.sytes.roneymaia.personalvr

import android.graphics.Bitmap

object SingletonControlCanvas{

    var bitmapCv: Bitmap? = null
        get() = field
        set(value) {
            field = value
        }
}