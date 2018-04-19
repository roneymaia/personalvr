package net.sytes.roneymaia.personalvr

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class CustomViewCanvas @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var canvasWidth: Int? = 0
    private var canvasHeigth: Int? = 0
    private var frameToDraw: Rect? = null
    private var whereToDraw: Rect? = null

    override fun onDraw(canvas: Canvas?) {
        val bitmapCvloc = SingletonControlCanvas.bitmapCv
        if (bitmapCvloc != null) {

            this@CustomViewCanvas.frameToDraw = Rect(0, 0, bitmapCvloc.width, bitmapCvloc.height)
            this@CustomViewCanvas.whereToDraw = Rect(0, 0, this@CustomViewCanvas.width, this@CustomViewCanvas.height)

            canvas!!.drawBitmap(bitmapCvloc, this@CustomViewCanvas.frameToDraw, this@CustomViewCanvas.whereToDraw, null)


        }else{
            super.onDraw(canvas)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        this@CustomViewCanvas.canvasWidth = w
        this@CustomViewCanvas.canvasHeigth = h
        super.onSizeChanged(w, h, oldw, oldh)
    }

}
