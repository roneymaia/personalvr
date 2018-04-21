package net.sytes.roneymaia.personalvr

import android.content.res.AssetManager

object SingletonControlCanvas{

    var imagensgif: Array<String?> = arrayOf<String?>()
        get() = field
        set(value) {
            field = value
        }

    var imageNumber: Int? = 0
        get() = field
        set(value) {
            field = value
        }

    var assets: AssetManager? = null
        get() = field
        set(value) {
            field = value
        }

}