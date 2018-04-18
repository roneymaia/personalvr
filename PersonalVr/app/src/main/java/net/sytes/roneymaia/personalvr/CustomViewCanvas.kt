package net.sytes.roneymaia.personalvr

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomViewCanvas @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {
        val bitmapCvloc = SingletonControlCanvas.bitmapCv
        if (bitmapCvloc != null) {
            canvas!!.drawBitmap(bitmapCvloc, 0f, 0f, Paint())
        }else{
            super.onDraw(canvas)
        }

    }

}
